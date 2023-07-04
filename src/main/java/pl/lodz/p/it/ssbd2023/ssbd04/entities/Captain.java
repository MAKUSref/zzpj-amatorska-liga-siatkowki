package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "captains")
@DiscriminatorValue("CAPTAIN")
@NamedQueries( {
        @NamedQuery(name = "Captain.findAll", query = "SELECT d FROM Captain d"),
        @NamedQuery(name = "Captain.findAllAccountsThatAreCaptain", query = "SELECT d.account FROM Captain d"),
        @NamedQuery(name = "Captain.getTeam", query = "SELECT d FROM Captain d WHERE d.team = :team")
})
public class Captain extends Role {

    @OneToOne(mappedBy = "captain")
    private Team team;

    public Captain() {
        this.setRoleType(RoleType.CAPTAIN);
    }
}
