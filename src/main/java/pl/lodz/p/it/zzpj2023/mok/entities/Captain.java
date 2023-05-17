package pl.lodz.p.it.zzpj2023.mok.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.zzpj2023.mzl.entities.Team;

@Entity
@Getter @Setter
@Table(name = "captains")
@DiscriminatorValue("CAPTAIN")
@NamedQueries( {
        @NamedQuery(name = "Captain.findAll", query = "SELECT d FROM Captain d"),
        @NamedQuery(name = "Captain.findAllAccountsThatAreCaptain", query = "SELECT d.user FROM Captain d"),
        @NamedQuery(name = "Captain.getTeam", query = "SELECT d FROM Captain d WHERE d.team = :team")
})
@AllArgsConstructor
public class Captain extends Role {

    @OneToOne(mappedBy="captain")
    private Team team;

    public Captain() {
        this.setRoleType(RoleType.CAPTAIN);
    }
}
