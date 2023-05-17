package pl.lodz.p.it.zzpj2023.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "players", indexes = {
        @Index(name = "player_team_fk", columnList = "team_id"),
        @Index(name = "player_game_squad_fk", columnList = "game_squad_id")
})
public class Player {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, updatable = false)
    @Getter @Setter
    private String firstName;

    @Column(name = "last_name", nullable = false, updatable = false)
    @Getter @Setter
    private String lastName;

    @Column(name = "age", nullable = false)
    @Getter @Setter
    private int age;

    @Column(name = "is_pro", nullable = false)
    @Getter @Setter
    private boolean isPro;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "game_squad_id")
    private GameSquad gameSquad;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
