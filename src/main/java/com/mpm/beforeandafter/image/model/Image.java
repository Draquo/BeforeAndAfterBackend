package com.mpm.beforeandafter.image.model;

import com.mpm.beforeandafter.category.model.Category;
import com.mpm.beforeandafter.user.model.StatusType;
import com.mpm.beforeandafter.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "city_name", length = 100)
    private String cityName;

    @ManyToMany(mappedBy = "favourites")
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Column(name = "approved_by_user_id")
    private Long approvedByUserId;

    @Column(name = "approved_date")
    private LocalDate approvedDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is mandatory.")
    private StatusType status;
}