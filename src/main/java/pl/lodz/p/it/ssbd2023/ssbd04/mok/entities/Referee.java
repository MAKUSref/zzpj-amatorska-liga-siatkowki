package pl.lodz.p.it.ssbd2023.ssbd04.mok.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.entities.Game;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "referees")
@Getter @Setter
@DiscriminatorValue("REFEREE")
public class Referee extends Role {

    @OneToMany(mappedBy = "referee")
    private Collection<Game> games = new ArrayList<>();

    public Referee() {
    }
}
