package com.ericko.evenor.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@Data
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

    @Column(nullable = false)
    private Boolean active;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL   )
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Role> roles;

    public User() {
    }

    public User(UUID id) {
        this.id = id;
    }

    public User(Boolean active, String name, String email, String password, String photo, List<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = active;
        this.photo = photo;
        this.roles = roles;
    }

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.active = active;
    }

}
