package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game_squads")
@NoArgsConstructor
public class GameSquad extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;



    @ManyToMany(mappedBy = "gameSquads", fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL
    })
    @Getter @Setter
    private List<Player> players = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    @Getter @Setter
    private Team team;


    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Getter @Setter
    private Game game;

    public void addPlayerToSquad(Player p){
        this.players.add(p);
        p.getGameSquads().add(this);
    }

    public void clearSquad(){
        for(Player p : this.players){
            p.getGameSquads().remove(this);
        }
        this.players.clear();
    }


    @Version
    @Column(name = "version", nullable = false)
    @Getter @Setter
    private long version;

    public GameSquad(Team team) {
        this.version = 1;
        this.team = team;
    }
}
