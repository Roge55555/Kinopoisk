package com.itech.kinopoisk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "session_rating")
public class SessionRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "rating")
    @PositiveOrZero
    private Double rating;

    @Column(name = "txt")
    @NotBlank
    private String txt;
}
