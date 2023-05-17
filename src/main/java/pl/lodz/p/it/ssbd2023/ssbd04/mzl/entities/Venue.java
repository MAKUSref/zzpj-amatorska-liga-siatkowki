package pl.lodz.p.it.ssbd2023.ssbd04.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venues")
public class Venue {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "address", nullable = false, updatable = false)
    @Getter @Setter
    private String address;

    @Column(name = "court_number", nullable = false, unique = true, updatable = false)
    @Getter @Setter
    private int courtNumber;

    @OneToMany(mappedBy = "venue")
    @Getter @Setter
    private List<Game> games = new ArrayList<>();

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
