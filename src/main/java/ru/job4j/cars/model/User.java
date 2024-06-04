package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "auto_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;
    @OneToMany(mappedBy = "auto_user", cascade = CascadeType.ALL)
    private Set<Post> autoPost;
}
