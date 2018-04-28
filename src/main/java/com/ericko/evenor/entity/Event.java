package com.ericko.evenor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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

    @Column
    private Boolean setParticipant;

    @Column
    private Boolean setComittee;

    @Column(nullable = false)
    @NonNull
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Column(nullable = false)
    @NonNull
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @ManyToMany(targetEntity = User.class)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<User> adminEvent;
}
