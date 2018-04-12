package com.ericko.evenor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.testng.annotations.ObjectFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    @NonNull
    private String description;

    @Column(nullable = false)
    @NonNull
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-mm-dd hh:mm:ss")
    private Date startDate;

    @Column(nullable = false)
    @NonNull
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-mm-dd hh:mm:ss")
    private Date endDate;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<User> comittee;

    public Event(){}

    public Event (String name, String description, Date startDate, Date endDate, List<User> comittee) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comittee = comittee;
    }
}
