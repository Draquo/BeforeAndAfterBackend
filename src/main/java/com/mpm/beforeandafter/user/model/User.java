package com.mpm.beforeandafter.user.model;

import com.mpm.beforeandafter.contactdetails.model.ContactDetails;
import com.mpm.beforeandafter.image.model.Image;
import com.mpm.beforeandafter.role.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
//@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @NotBlank(message = "User name is mandatory.")
    @Size(min = 2, max = 25, message = "User name must be between 2 and 25 characters long.")
    @NotNull
    private String name;

    @Column(name = "email", length = 100, unique = true)
    @NotNull
    @NotBlank(message = "User email is mandatory.")
    @Size(min = 5, max = 50, message = "User email must be between 5 and 50 characters long.")
    private String email;

    @Column(name = "password", length = 500)//TODO security hash?
    @NotBlank(message = "User password is mandatory.")
    @Size(min = 8, max = 500, message = "User password must be between 8 and 50 characters long.")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(name = "city_name", length = 100)
    @Size(min = 2, max = 100, message = "User password must be between 2 and 100 characters long.")
    private String cityName;

    @Column(name = "contact_id") //TODO relation with table addresses oneToOne
    private Integer contactId;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "about_me", columnDefinition = "TEXT")
    @Size(min = 10, max = 5000, message = "About me must be between 10 and 500 characters.")
    private String aboutMe;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull(message = "Status is mandatory.")
    private StatusType status;

    @Column(name = "is_approved", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @NotNull(message = "Approved status is mandatory.")
    private boolean isApproved;

    @Column(name = "approved_by_user_id")
    private Integer approvedByUserId;

    @Column(name = "approved_date")
    private String approvedDate;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id")
    )
    private Set<Image> favourites = new HashSet<>();
}