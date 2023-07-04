package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "overall_team_scores", indexes = {
        @Index(name = "ots_team_fk", columnList = "team_id"),
        @Index(name = "ots_scoreboard_fk", columnList = "scoreboard_id")
})
@NoArgsConstructor
@NamedQueries( {
        @NamedQuery(name = "OverallTeamScore.getByTeamAndScoreboard", query = "SELECT ots FROM OverallTeamScore ots WHERE ots.scoreboard.id = :scoreboard AND ots.team.id = :team"),
})
public class OverallTeamScore extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter @Setter
    private UUID id;

    @ManyToOne
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
    @Column(name = "version", nullable = false)
    @Getter @Setter
    private long version;

    public OverallTeamScore(int wonGames, int lostGames, int wonSets, int lostSets) {
        this.lostGames = lostGames;
        this.wonGames = wonGames;
        this.lostSets = lostSets;
        this.wonSets = wonSets;
    }
}
