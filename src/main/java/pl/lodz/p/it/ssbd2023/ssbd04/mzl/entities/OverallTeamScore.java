package pl.lodz.p.it.ssbd2023.ssbd04.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "overall_team_scores", indexes = {
        @Index(name = "ots_team_fk", columnList = "team_id"),
        @Index(name = "ots_scoreboard_fk", columnList = "scoreboard_id")
})
public class OverallTeamScore {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false, updatable = false)
    @Getter @Setter
    private Team team;

    @Column(name = "points", nullable = false)
    @Getter @Setter
    private int points;

    @Column(name = "won_games", nullable = false)
    @Getter @Setter
    private int wonGames;

    @Column(name = "lost_games", nullable = false)
    @Getter @Setter
    private int lostGames;

    @Column(name = "won_sets", nullable = false)
    @Getter @Setter
    private int wonSets;

    @Column(name = "lost_sets", nullable = false)
    @Getter @Setter
    private int lostSets;

    @ManyToOne
    @JoinColumn(name = "scoreboard_id", nullable = false, updatable = false)
    @Getter @Setter
    private Scoreboard scoreboard;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
