package com.ericko.evenor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String name;

    @Column(nullable = false, unique = true)
    @NotNull
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String photo;

    @Column
    private Boolean active;

    @Column
    private Integer experience;

    @ManyToMany(targetEntity = Role.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Role> roles;

    public User(UUID id) {
        this.id = id;
    }

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.active = user.active;
        this.photo = user.photo;
        this.roles = user.roles;
        this.experience = user.experience;
    }
}
