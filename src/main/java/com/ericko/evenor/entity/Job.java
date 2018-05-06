package com.ericko.evenor.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;
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

    @Column(nullable = true)
    @DateTimeFormat
    private Date startDate;

    @Column(nullable = true)
    @DateTimeFormat
    private Date endDate;

    @ManyToOne(targetEntity = Division.class)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Division division;

    @ManyToOne(targetEntity = JobComment.class)
    private List<JobComment> comments;
}
