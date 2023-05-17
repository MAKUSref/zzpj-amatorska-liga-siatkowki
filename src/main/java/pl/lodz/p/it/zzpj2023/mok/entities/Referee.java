package pl.lodz.p.it.zzpj2023.mok.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.zzpj2023.mzl.entities.Game;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "referees")
@Getter @Setter
@DiscriminatorValue("REFEREE")
@NamedQueries( {
        @NamedQuery(name = "Referee.findAll", query = "SELECT d FROM Referee d"),
        @NamedQuery(name = "Referee.findAllAccountsThatAreReferee", query = "SELECT d.user FROM Referee d"),
        @NamedQuery(name = "Referee.getTeam", query = "SELECT d FROM Referee d WHERE d.games = :team")
})
public class Referee extends Role {

    @OneToMany(mappedBy = "referee")
    private Collection<Game> games = new ArrayList<>();

    public Referee() {
        this.setRoleType(RoleType.REFEREE);
    }
}
