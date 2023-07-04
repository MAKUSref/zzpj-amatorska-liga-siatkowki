package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "coaches")
@DiscriminatorValue("COACH")
@NamedQueries( {
        @NamedQuery(name = "Coach.findAll", query = "SELECT d FROM Coach d"),
        @NamedQuery(name = "Coach.findAllAccountsThatAreCoach", query = "SELECT d.account FROM Coach d"),
        @NamedQuery(name = "Coach.getTeam", query = "SELECT d FROM Coach d WHERE d.team = :team")
      //@NamedQuery(name = "Coach.findCoachesByAccountId", query = "SELECT c.id FROM Coach c WHERE c.team = :teamId ")

        })
public class Coach extends Role {

    @OneToOne(mappedBy="coach")
    private Team team;

    public Coach() {
        this.setRoleType(RoleType.COACH);
    }
}
