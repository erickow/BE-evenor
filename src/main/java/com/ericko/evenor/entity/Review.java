package com.ericko.evenor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private Integer leadership;

    @Column
    private Integer authority;

    @Column
    private Integer decision;

    @Column
    private Integer flexibility;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = User.class)
    private User reviewer;

    @ManyToOne(targetEntity = Event.class)
    private Event event;

}
