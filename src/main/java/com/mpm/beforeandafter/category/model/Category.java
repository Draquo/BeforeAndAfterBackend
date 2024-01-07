package com.mpm.beforeandafter.category.model;

import com.mpm.beforeandafter.image.model.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 25)
    @NotBlank(message = "Category name is mandatory.")
    @Size(min=2, max=25,message="Category name must be between 2 and 25 characters long.")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Image> images = new HashSet<>();
}