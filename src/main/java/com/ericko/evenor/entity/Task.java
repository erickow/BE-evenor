package com.ericko.evenor.entity;

import com.sun.javafx.font.freetype.FTFactory;
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

    @Column(nullable = false)
    @NotBlank
    @NonNull
    private String description;

    @Column(nullable = false)
    @NonNull
    @NotBlank
    @DateTimeFormat
    private Date startDate;

    @Column(nullable = true)
    @NonNull
    @NotBlank
    @DateTimeFormat
    private Date endDate;

    @ManyToOne(targetEntity = Event.class, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Event event;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<User> volunteer;

}
