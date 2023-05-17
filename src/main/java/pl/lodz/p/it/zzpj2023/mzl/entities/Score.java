package pl.lodz.p.it.zzpj2023.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @OneToMany(mappedBy = "score", cascade = CascadeType.REMOVE)
    private List<Set> sets = new ArrayList<>();

    @Column(name = "scoreboard_points_a", nullable = false, updatable = false)
    @Getter @Setter
    private int scoreboardPointsA;

    @Column(name = "scoreboard_points_b", nullable = false, updatable = false)
    @Getter @Setter
    private int scoreboardPointsB;

    @OneToMany(mappedBy = "score")
    @Getter @Setter
    private List<Game> games = new ArrayList<>();

    @Column(name = "is_approved", nullable = false)
    @Getter @Setter
    private boolean isApproved;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
