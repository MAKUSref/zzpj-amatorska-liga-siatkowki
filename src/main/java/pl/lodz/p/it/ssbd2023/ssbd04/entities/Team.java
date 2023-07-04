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
@Table(name = "teams", indexes = {
        @Index(name = "team_captain_fk", columnList = "captain_id"),
        @Index(name = "team_coach_fk", columnList = "coach_id"),
        @Index(name = "team_league_fk", columnList = "league_id")
})
@NamedQueries( {
        @NamedQuery(name = "Team.findByCoach", query = "SELECT d FROM Team d WHERE d.coach = :coach"),
        @NamedQuery(name = "Team.findByCaptain", query = "SELECT d FROM Team d WHERE d.captain = :captain"),
        @NamedQuery(name = "Team.findByManager", query = "SELECT d FROM Team d JOIN d.manager m WHERE m = :manager"),
        @NamedQuery(name = "Team.getByTeamName", query = "SELECT d FROM Team d WHERE d.teamName = :name"),
        @NamedQuery(name = "Team.findNotSubmitted", query = "SELECT d FROM Team d WHERE d.league = null")
})
@Getter @Setter
@NoArgsConstructor
public class Team extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @Column(name = "team_name", nullable = false, unique = true)
    private String teamName;

    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "team", fetch = FetchType.EAGER)
    private List<Manager> manager = new ArrayList<>();

    @OneToOne
    private Captain captain;

    @OneToOne
    private Coach coach;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<GameSquad> gameSquads = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<OverallTeamScore> overallTeamScores = new ArrayList<>();

    public Team(String teamName, String city) {
        this.teamName = teamName;
        this.city = city;
        this.isApproved = false;
    }

    public void addManager(Manager newManager) {
        manager.add(newManager);
    }


    public Team(String teamName, String city, League league) {
        this.teamName = teamName;
        this.city = city;
        this.isApproved = false;
        this.league = league;
    }
}
