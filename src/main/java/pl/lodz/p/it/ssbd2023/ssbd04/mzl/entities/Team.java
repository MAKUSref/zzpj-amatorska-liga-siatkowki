package pl.lodz.p.it.ssbd2023.ssbd04.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.entities.Captain;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.entities.Coach;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.entities.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teams", indexes = {
        @Index(name = "team_captain_fk", columnList = "captain_id"),
        @Index(name = "team_coach_fk", columnList = "coach_id"),
        @Index(name = "team_league_fk", columnList = "league_id")
})
public class Team {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @Column(name = "team_name", nullable = false)
    @Getter @Setter
    private String teamName;

    @Column(name = "city", nullable = false)
    @Getter @Setter
    private String city;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "team")
    private List<Manager> manager = new ArrayList<>();

    @OneToOne
    private Captain captain;

    @OneToOne
    private Coach coach;

    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<GameSquad> gameSquads = new ArrayList<>();

    @ManyToMany(mappedBy = "teams")
    private List<Round> rounds = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @Column(name = "is_approved", nullable = false)
    @Getter @Setter
    private boolean isApproved;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
