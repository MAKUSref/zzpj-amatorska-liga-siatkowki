package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "referees")
@Getter @Setter
@DiscriminatorValue("REFEREE")
@NamedQueries( {
        @NamedQuery(name = "Referee.findAll", query = "SELECT d FROM Referee d"),
        @NamedQuery(name = "Referee.findAllAccountsThatAreReferee", query = "SELECT d.account FROM Referee d"),
        @NamedQuery(name = "Referee.getTeam", query = "SELECT d FROM Referee d WHERE d.games = :games")
})
public class Referee extends Role {

    @OneToMany(mappedBy = "referee", fetch = FetchType.EAGER)
    private Collection<Game> games = new ArrayList<>();

    public Referee() {
        this.setRoleType(RoleType.REFEREE);
    }
}
