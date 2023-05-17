package pl.lodz.p.it.ssbd2023.ssbd04.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.entities.Referee;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "games", indexes = {
        @Index(name = "game_venue_fk", columnList = "venue_id"),
        @Index(name = "game_squad_a_fk", columnList = "game_squad_a_id"),
        @Index(name = "game_squad_b_fk", columnList = "game_squad_b_id"),
        @Index(name = "game_referee_id", columnList = "referee_id"),
        @Index(name = "game_score_fk", columnList = "score_id")
})
public class Game {
    @Id
    // @SequenceGenerator(name = "GameSeqGen", sequenceName = "GAME_PK_SEQ")
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GameSeqGen")
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "game_squad_a_id")
    private GameSquad teamA;

    @ManyToOne
    @JoinColumn(name = "game_squad_b_id")
    private GameSquad teamB;

    @ManyToOne
    @JoinColumn(name = "referee_id", nullable = false)
    private Referee referee;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "score_id")
    private Score score;

    @Column(name = "start_time", nullable = false)
    @Getter @Setter
    private Date startTime;

    @Column(name = "end_time", updatable = false)
    @Getter @Setter
    private Date endTime;

    @Column(name = "queue", nullable = false, updatable = false)
    @Getter @Setter
    private int queue;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
