package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "leagues")
@NamedQueries({
        @NamedQuery(name = "League.findAllTeamsInLeague", query = "SELECT t FROM Team t WHERE t.isApproved = true AND t.league = :league"),
        @NamedQuery(name = "League.findAllPendingRequestsInLeague", query = "SELECT t FROM Team t WHERE t.isApproved = false AND t.league = :league"),
        @NamedQuery(name = "League.getLeaguesFromSeason", query = "SELECT l from League l WHERE l.season = :season"),
        @NamedQuery(name = "League.getLowestLeagueFromSeason", query = "SELECT l from League l WHERE l.season = :season ORDER BY l.leagueNumber DESC LIMIT 1"),
        @NamedQuery(name = "League.findAllTeamsByLeaguesByIds", query = "SELECT t FROM Team t WHERE t.isApproved = true AND t.league.id in :leaguesIds")
})
@Getter @Setter
public class League extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @Column(name = "league_number")
    private int leagueNumber;

    @ManyToMany(mappedBy = "leagues", fetch = FetchType.EAGER)
    private List<Round> rounds = new ArrayList<>();

    @OneToMany(mappedBy = "league", fetch = FetchType.EAGER)
    private List<Team> teams = new ArrayList<>();

    @Pattern(regexp = "\\d{4}/\\d{4}", message = "Niepoprawny format. Oczekiwany format: rok/rok")
    @Column(name = "season")
    private String season;

    public void addTeam(Team team) {
        teams.add(team);
    }

    public League() {
        id = UUID.randomUUID();
    }
}
