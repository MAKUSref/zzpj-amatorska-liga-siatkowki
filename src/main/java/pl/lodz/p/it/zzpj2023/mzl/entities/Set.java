package pl.lodz.p.it.zzpj2023.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "sets")
public class Set {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "team_a_points", nullable = false, updatable = false)
    @Getter @Setter
    private int teamAPoints;

    @Column(name = "team_b_points", nullable = false, updatable = false)
    @Getter @Setter
    private int teamBPoints;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "score_id",nullable = false)
    private Score score;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
