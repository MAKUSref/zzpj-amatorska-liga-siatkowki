package pl.lodz.p.it.zzpj2023.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rounds", indexes = {
        @Index(name = "round_league_fk", columnList = "league_id"),
        @Index(name = "round_timetable_fk", columnList = "timetable_id"),
        @Index(name = "round_scoreboard_fk", columnList = "scoreboard_id")
})
public class Round {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @ManyToMany
    @JoinTable(name = "round_teams")
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "round")
    private List<Day> days = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false, updatable = false)
    private League league;

    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false, updatable = false)
    private Timetable timetable;

    @ManyToOne
    @JoinColumn(name = "scoreboard_id", nullable = false, updatable = false)
    private Scoreboard scoreboard;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
