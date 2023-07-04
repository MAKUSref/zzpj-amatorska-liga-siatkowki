package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "sets")
@NamedQueries({
        @NamedQuery(name = "Set.getAllSets", query = "SELECT s FROM Set s WHERE s.score.id= :id")
})
@NoArgsConstructor
public class Set extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @Column(name = "team_a_points", nullable = false, updatable = false)
    @Getter @Setter
    private int teamAPoints;

    @Column(name = "team_b_points", nullable = false, updatable = false)
    @Getter @Setter
    private int teamBPoints;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "score_id")
    @Getter
    @Setter
    private Score score;

    public Set(UUID id, int teamAPoints, int teamBPoints) {
        this.id = id;
        this.teamAPoints = teamAPoints;
        this.teamBPoints = teamBPoints;
    }
}
