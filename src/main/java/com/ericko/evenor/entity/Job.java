package com.ericko.evenor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Job {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column
    private Integer position;

    @Column
    private String description;

    @Column
    private String level;

    @Column
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private Date startDate;

    @Column
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private Date endDate;

    @Column
    private Boolean completion;

    @ManyToMany(targetEntity = EventComittee.class)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<EventComittee> comittees;

}
