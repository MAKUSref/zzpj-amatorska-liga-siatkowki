package pl.lodz.p.it.zzpj2023.mok.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import pl.lodz.p.it.zzpj2023.mzl.entities.Team;

@Entity
@Table(name = "coaches")
@DiscriminatorValue("COACH")
@NamedQueries( {
        @NamedQuery(name = "Coach.findAll", query = "SELECT d FROM Coach d"),
        @NamedQuery(name = "Coach.findAllAccountsThatAreCoach", query = "SELECT d.user FROM Coach d"),
        @NamedQuery(name = "Coach.getTeam", query = "SELECT d FROM Coach d WHERE d.team = :team")
})
@AllArgsConstructor
public class Coach extends Role {

    @OneToOne(mappedBy="coach")
    private Team team;

    public Coach() {
        this.setRoleType(RoleType.COACH);
    }
}
