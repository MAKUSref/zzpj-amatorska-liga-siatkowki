package pl.lodz.p.it.zzpj2023.mok.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.zzpj2023.mzl.entities.Team;

@Entity
@Getter @Setter
@Table(name = "managers")
@DiscriminatorValue("MANAGER")
public class Manager extends Role {

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Manager() {
    }
}
