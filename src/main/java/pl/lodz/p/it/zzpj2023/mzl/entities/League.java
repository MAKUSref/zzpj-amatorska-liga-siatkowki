package pl.lodz.p.it.zzpj2023.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "leagues")
public class League {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @OneToMany(mappedBy = "league", cascade = CascadeType.REMOVE)
    private List<Round> rounds = new ArrayList<>();

    @OneToMany(mappedBy = "league")
    private List<Team> teams = new ArrayList<>();

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
