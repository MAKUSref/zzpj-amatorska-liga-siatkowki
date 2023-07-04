package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venues")
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Venue.getAllVenueByIds", query = "SELECT v FROM Venue v WHERE v.id in :venuesIds"),
        @NamedQuery(name = "Venue.getAllVenues", query = "SELECT v FROM Venue v"),
})
public class Venue extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
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
    @Column(name = "version", nullable = false)
    @Getter @Setter
    private long version;

    public Venue(String address, int courtNumber) {
        this.address = address;
        this.courtNumber = courtNumber;
    }
}
