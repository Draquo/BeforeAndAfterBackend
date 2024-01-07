package com.mpm.beforeandafter.user.service;

import com.mpm.beforeandafter.contactdetails.service.ContactDetailsService;
import com.mpm.beforeandafter.email.service.EmailService;
import com.mpm.beforeandafter.exception.ResourceNotFoundException;
import com.mpm.beforeandafter.role.model.Role;
import com.mpm.beforeandafter.role.repository.RoleRepository;
import com.mpm.beforeandafter.role.service.RoleService;
import com.mpm.beforeandafter.role.type.RoleType;
import com.mpm.beforeandafter.security.jwt.JwtUtils;
import com.mpm.beforeandafter.user.dto.*;
import com.mpm.beforeandafter.user.model.StatusType;
import com.mpm.beforeandafter.user.model.User;
import com.mpm.beforeandafter.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final String USER_NOT_FOUND_MSG_TEMPLATE_LOG_ERROR =
            "There isn't user with the given ID: {}";
    private final String USER_NOT_FOUND_MSG_TEMPLATE_EXCEPTION =
            "There isn't user with the given ID: ";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    private final EmailService emailService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ContactDetailsService contactDetailsService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService,
                           EmailService emailService, UserMapper userMapper, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtUtils jwtUtils, ContactDetailsService contactDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.emailService = emailService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.contactDetailsService = contactDetailsService;
    }

    @Override
    public List<GetUserResponseDto> getUsers(RoleType roleType) {
        log.debug("Fetching all users");
        List<User> users;
        if (roleType == null) {
            users = userRepository.findAll();
        } else {
            Role providedRole = roleRepository.findByName(roleType);
            users = userRepository.findAllByRolesIn(Set.of(providedRole.getId()));
        }
        log.info("Getting all users (count): {}", users.size());
        return users.stream()
                .map(userMapper::mapToGetUserResponseDto)
                .toList();
    }

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto userDto, RoleType roleType) {
        //TODO: delete sout when security developed
        System.out.println(userDto);
        System.out.println(roleType);
        Role role = roleService.findByName(roleType);
        User user = new User();
        user.setName(userDto.getUserName());
        user.setEmail(userDto.getUserEmail());
        user.setPassword(passwordEncoder.encode(userDto.getUserPassword()));
        user.setRoles(Set.of(role));
        user.setStatus(StatusType.TO_REVIEW);
        User createdUser = userRepository.save(user);
        contactDetailsService.createAndGetDefaultContactDetails(createdUser.getId());

        emailService.sendRegistrationEmail(createdUser.getName(), createdUser.getEmail())
                .thenAcceptAsync(emailResponse -> {
                    if (emailResponse.sentSuccessfully()) {
                        log.info("Registration email sent successfully to {}", createdUser.getEmail());
                    } else {
                        log.error("Failed to send registration email to {}", createdUser.getEmail());
                    }
                });

        return userMapper.mapToCreateUserResponseDto(createdUser);
    }

    @Override
    public GetUserResponseDto getUserById(Long userId) {
        log.debug("Getting user by id: {}", userId);
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> {
                    log.error("There is no user with the given id: {}", userId);
                    return new RuntimeException(
                            "User not found with id: {}" + userId);
                });
        return userMapper.mapToGetUserResponseDto(user);
    }

    @Override
    public GetAboutMeResponseDto getAboutMeByUserId(Long userId) {
        log.debug("Getting user about me by id: {}", userId);
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> {
                    log.error("There is no user with the given id: {}", userId);
                    return new RuntimeException(
                            "User not found with id: {}" + userId);
                });
        return userMapper.mapToGetAboutMeResponseDto(user);
    }

    @Override
    public CreateAboutMeResponseDto updateUserByAboutMe(Long userId, CreateAboutMeRequestDto aboutMe) {
        log.debug("Getting user by id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("There is no user with the given id: {}", userId);
                    return new RuntimeException(
                            "User not found with id: {}" + userId);
                });
        user.setAboutMe(aboutMe.getAboutMe());
        userRepository.save(user);
        return userMapper.mapToCreateAboutMeResponseDto(user);
    }

    @Override
    public CreateUserResponseDto updateUser(Long userId, CreateUserRequestDto userDto) {
        log.debug("Getting user by id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("There is no user with the given id: {}", userId);
                    return new RuntimeException(
                            "User not found with id: {}" + userId);
                });
        user.setName(userDto.getUserName());

        userRepository.save(user);

        return userMapper.mapToCreateUserResponseDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.debug("User with id:{}", userId);
        userRepository
                .findById(userId)
                .ifPresentOrElse(user -> {
                            userRepository.delete(user);
                            log.info("User with id: {} deleted successfully", userId);
                        },
                        () -> {
                            log.error(USER_NOT_FOUND_MSG_TEMPLATE_LOG_ERROR, userId);
                            throw new ResourceNotFoundException(USER_NOT_FOUND_MSG_TEMPLATE_EXCEPTION + userId);
                        });
    }

    @Override

    public CreateAvatarResponseDto createAvatar(MultipartFile file, CreateAvatarRequestDto request)
            throws FileUploadException {

        System.out.println(file.getOriginalFilename());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.error("There is no user with the given id: {}", request.getUserId());
                    return new RuntimeException(
                            "User not found with id: {}" + request.getUserId());
                });

        System.out.println(user);

        try {
            user.setAvatar(file.getBytes());
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

        } catch (IOException e) {
            throw new FileUploadException("File not uploaded.");
        }

        userRepository.save(user);

        return userMapper.mapToCreateAvatarResponseDto(user);

    }

    public Authentication getAuthentication(SignInRequestDto loginRequest) {
        //TODO: delete sout when security developed
        System.out.println("LoginRequest: " + loginRequest);
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUserEmail(),
                                loginRequest.getUserPassword()));
        return authentication;
    }

    @Override
    public String getSecurityContextAndJwt(SignInRequestDto loginRequest, Authentication authentication) {
        String userEmail = loginRequest.getUserEmail();
        com.mpm.beforeandafter.user.model.User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication, user.getId());
    }

    @Override
    public org.springframework.security.core.userdetails.User getUserDetails(Authentication authentication) {
        return (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    }

    @Override
    public List<String> getRoles(org.springframework.security.core.userdetails.User userDetails) {
        return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();
    }

    @Override
    public SignInResponseDto createJwtResponse(String jwt, String username, List<String> roles) {
        return new SignInResponseDto(jwt, username, roles);

    }
}
