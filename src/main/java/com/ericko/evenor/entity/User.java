package com.ericko.evenor.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.annotation.ManagedBean;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
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
    @NotNull
    @NotBlank
    private String password;

    private String photo;
    @ManyToMany
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Role> role;

}
