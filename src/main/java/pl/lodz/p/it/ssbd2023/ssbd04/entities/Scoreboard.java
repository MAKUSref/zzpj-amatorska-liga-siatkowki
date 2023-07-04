package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "scoreboards")
@NamedQueries( {
        @NamedQuery(name = "Scoreboard.getByRound", query = "SELECT s FROM Scoreboard s WHERE s.round.id = :round "),
})
public class Scoreboard extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter @Setter
    private UUID id;

    @OneToOne(mappedBy = "scoreboard")
    @Getter
    private Round round;

    @OneToMany(mappedBy = "scoreboard", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @Getter
    private List<OverallTeamScore> teamsScores;
}
