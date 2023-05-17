package pl.lodz.p.it.ssbd2023.ssbd04.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game_squads", indexes = {
        @Index(name = "team_fk", columnList = "team_id"),
})
public class GameSquad {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @OneToMany(mappedBy = "gameSquad")
    private List<Player> players = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false, updatable = false)
    private Team team;

    @OneToMany(mappedBy = "teamA")
    @Column(name = "games_team_a", nullable = false)
    private List<Game> gamesTeamA = new ArrayList<>();

    @OneToMany(mappedBy = "teamB")
    @Column(name = "games_team_b", nullable = false)
    private List<Game> gamesTeamB = new ArrayList<>();

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
