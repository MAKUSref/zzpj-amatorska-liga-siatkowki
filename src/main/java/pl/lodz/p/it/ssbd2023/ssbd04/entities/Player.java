package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "players", indexes = {
        @Index(name = "player_team_fk", columnList = "team_id")
})
@Getter @Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor

public class Player extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Setter(value = AccessLevel.NONE)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "age", nullable = false)
    @NotNull
    private int age;

    @Column(name = "is_pro", nullable = false)
    @NotNull
    private boolean isPro;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            })
    @JoinTable(name = "game_squad_players",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "game_squad_id")
    )
    @Getter
    @Setter
    private List<GameSquad> gameSquads;

    public Player(String firstName, String lastName, int age, boolean isPro, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isPro = isPro;
        this.team = team;
    }

}
