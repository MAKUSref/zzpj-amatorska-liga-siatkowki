package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rounds", indexes = {
        @Index(name = "round_timetable_fk", columnList = "timetable_id"),
        @Index(name = "round_scoreboard_fk", columnList = "scoreboard_id")
})
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Round extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @Column(name = "round_number", nullable = false)
    private int roundNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "leagues_rounds",
            joinColumns = { @JoinColumn(name = "round_id") },
            inverseJoinColumns = { @JoinColumn(name = "league_id") }
    )
    private List<League> leagues = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false, updatable = false)
    private Timetable timetable;

    @OneToMany(mappedBy = "round", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<Game> games = new ArrayList<>();

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "scoreboard_id", nullable = false, updatable = false)
    private Scoreboard scoreboard;

    public void addLeague(League league) {
        leagues.add(league);
    }
    public void addGame(Game game) {
        games.add(game);
    }

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
        scoreboard = new Scoreboard();
    }
}
