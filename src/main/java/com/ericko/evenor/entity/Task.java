package com.ericko.evenor.entity;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String name;

    @Column
    private Integer position;

    @ManyToOne(targetEntity = Event.class)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Event event;

    @ManyToMany(targetEntity = Job.class)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Job> jobs;

}
