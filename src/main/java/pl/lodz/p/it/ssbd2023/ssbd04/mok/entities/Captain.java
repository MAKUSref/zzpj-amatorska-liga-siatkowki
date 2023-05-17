package pl.lodz.p.it.ssbd2023.ssbd04.mok.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.entities.Team;

@Entity
@Getter @Setter
@Table(name = "captains")
@DiscriminatorValue("CAPTAIN")
public class Captain extends Role {

    @OneToOne(mappedBy="captain")
    private Team team;

    public Captain() {
    }
}
