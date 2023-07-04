package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "scores")
public class Score extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @OneToMany(mappedBy = "score", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<Set> sets = new ArrayList<>();

    @Column(name = "scoreboard_points_a", nullable = false)
    @Getter @Setter
    private int scoreboardPointsA;

    @Column(name = "scoreboard_points_b", nullable = false)
    @Getter
    @Setter
    private int scoreboardPointsB;

    @OneToMany(mappedBy = "score")
    @Getter @Setter
    private List<Game> games = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "approvalTeamA", nullable = false)
    @Getter
    @Setter
    private ScoreDecision approvalTeamA;

    @Enumerated(EnumType.STRING)
    @Column(name = "approvalTeamB", nullable = false)
    @Getter
    @Setter
    private ScoreDecision approvalTeamB;

    public boolean isApproved() {
        return approvalTeamA.equalsName(ScoreDecision.APPROVED.value) &&
               approvalTeamB.equalsName((ScoreDecision.APPROVED.value));
    }

    public Score(UUID id){
        this.id = id;
    }
}
