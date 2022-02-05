package com.itech.kinopoisk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @Column(name = "name", unique = true)
    @Size(min = 3)
    @NotBlank
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_start")
    @Future
    private LocalDateTime dateStart;

    @Column(name = "max_user_count")
    @Positive
    private Long maxUserCount;

    @JsonIgnore
    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<SessionMessage> sessionMessageList;

    @JsonIgnore
    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<UserOfSession> userOfSessionList;

    @JsonIgnore
    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<SessionRating> sessionRatingList;

//    @JsonIgnore
//    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private List<BlocklistFilm> blocklistFilmList;
}
