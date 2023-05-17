package pl.lodz.p.it.zzpj2023.mok.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import pl.lodz.p.it.zzpj2023.mzl.entities.Team;

@Entity
@Table(name = "coaches")
@DiscriminatorValue("COACH")
public class Coach extends Role {

    @OneToOne(mappedBy="coach")
    private Team team;

    public Coach() {
    }
}
