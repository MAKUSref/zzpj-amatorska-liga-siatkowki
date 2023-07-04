package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "timetables")
@NamedQueries(
        @NamedQuery(name = "Timetable.getTimetablesBetween", query = "select t from Timetable t where t.endDate > :startDate and t.startDate < :endDate")
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Timetable extends AbstractEntity {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Getter
    private UUID id;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @OneToMany(mappedBy = "timetable", cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
    private List<Round> round = new ArrayList<>();

    public Timetable(Round round1, Round round2) {
        round.add(round1);
        round.add(round2);
    }
}
