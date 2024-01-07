package com.mpm.beforeandafter.user.controller;

import com.mpm.beforeandafter.role.type.RoleType;
import com.mpm.beforeandafter.user.dto.*;
import com.mpm.beforeandafter.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<GetUserResponseDto> getAllUsers(@RequestParam(required = false) RoleType roleType) {
        return userService.getUsers(roleType);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDto createUser(@RequestBody CreateUserRequestDto user) {

        return userService.createUser(user, RoleType.ROLE_USER);
    }

    @GetMapping("{id}")
    public GetUserResponseDto getUserById(@PathVariable("id") Long userId) {
        log.debug("Getting user by id: {}", userId);
        return userService.getUserById(userId);
    }

    @GetMapping("/{id}/about_me")
    public GetAboutMeResponseDto getUserAboutMe(@PathVariable("id") Long userId) {
        log.debug("Getting user about me by id: {}", userId);
        return userService.getAboutMeByUserId(userId);
    }

    @PatchMapping("/{id}/about_me")
    public CreateAboutMeResponseDto updateUserAboutMe(@PathVariable("id") Long userId,
                                                      @RequestBody CreateAboutMeRequestDto aboutMe) {
        log.debug("Updating about me of user with id: {} with data: {}", userId, aboutMe);
        CreateAboutMeResponseDto userWithUpdatedAboutMe = userService.updateUserByAboutMe(userId, aboutMe);
        log.info("User about me updated: {}", userId);
        return userWithUpdatedAboutMe;
    }

    @PutMapping("{id}")
    public CreateUserResponseDto updateUser(@PathVariable("id") Long userId,
                                            @RequestBody CreateUserRequestDto user) {
        return userService.updateUser(userId, user);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }

    @PatchMapping(consumes = "multipart/form-data")
    public CreateAvatarResponseDto updateAvatar(
            @RequestParam MultipartFile file,
            @RequestParam Long userId
            ){
        System.out.println("file.OriginalFileName = " + file.getOriginalFilename());

        String message = "";
        CreateAvatarRequestDto createAvatarRequestDto = new CreateAvatarRequestDto();
        createAvatarRequestDto.setUserId(userId);

        try {
           CreateAvatarResponseDto avatarResponseDto = userService.createAvatar(file,createAvatarRequestDto);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            System.out.println(message);
            return avatarResponseDto;
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            System.out.println(message);
            return null;
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public CreateAvatarResponseDto createAvatar(
            @RequestParam MultipartFile file,
            @RequestParam Long userId
    ){
        System.out.println("file.OriginalFileName = " + file.getOriginalFilename());

        String message = "";
        CreateAvatarRequestDto createAvatarRequestDto = new CreateAvatarRequestDto();
        createAvatarRequestDto.setUserId(userId);

        try {
            CreateAvatarResponseDto avatarResponseDto = userService.createAvatar(file,createAvatarRequestDto);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            System.out.println(message);
            return avatarResponseDto;
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            System.out.println(message);
            return null;
        }
    }



}