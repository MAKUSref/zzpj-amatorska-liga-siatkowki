package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "games", indexes = {
        @Index(name = "game_venue_fk", columnList = "venue_id"),
        @Index(name = "game_referee_id", columnList = "referee_id"),
        @Index(name = "game_score_fk", columnList = "score_id")
})
@NamedQueries( {
        @NamedQuery(name = "Game.getByTeam", query = "SELECT g FROM Game g WHERE g.teamA.team.id = :team OR g.teamB.team.id = :team "),
        @NamedQuery(name = "Game.getByTeamAndRound", query = "SELECT g FROM Game g JOIN g.round r WHERE (g.teamA.team.id = :team OR g.teamB.team.id = :team) AND r.id = :round"),
        @NamedQuery(name = "Game.getRefereeGames", query = "SELECT g FROM Game g JOIN Role r WHERE (g.referee.id = r.id AND r.account.id = :refereeId)")
})
public class Game extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    @Getter @Setter
    private Venue venue;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @Getter @Setter
    @JoinColumn(name = "teamAGameSquad", nullable = false)
    private GameSquad teamA;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @Getter @Setter
    @JoinColumn(name = "teamBGameSquad", nullable = false)
    private GameSquad teamB;

    @ManyToOne
    @JoinColumn(name = "referee_id")
    @Getter @Setter
    private Referee referee;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "score_id")
    @Getter @Setter
    private Score score;

    @Column(name = "start_time", nullable = false)
    @Getter @Setter
    private LocalDateTime startTime;

    @Column(name = "end_time", updatable = false)
    @Getter @Setter
    private LocalDateTime endTime;

    @Column(name = "queue", nullable = false, updatable = false)
    @Getter @Setter
    private int queue;

    @Column(name = "postpone_date_request")
    @Getter @Setter
    private LocalDateTime postponeDateRequest;

    @Column(name = "is_postponed")
    @Getter @Setter
    private boolean isPostponed;

    @Column(name = "is_postpone_request")
    @Getter @Setter
    private boolean postponeRequest = false;

    @ManyToOne()
    @JoinColumn(name="requesting_account")
    @Getter @Setter
    private Account requestingAccount = null;

    @ManyToOne()
    @JoinColumn(name="round_id")
    @Getter @Setter
    private Round round;
}
