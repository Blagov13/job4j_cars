package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "auto_user")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @EqualsAndHashCode.Include
    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> autoPost;

    private String name;
    private String phone;
}
