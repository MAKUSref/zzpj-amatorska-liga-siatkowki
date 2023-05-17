package pl.lodz.p.it.ssbd2023.ssbd04.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "timetables")
public class Timetable {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @OneToMany(mappedBy = "timetable")
    private List<Day> days = new ArrayList<>();

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.REMOVE)
    private List<Round> rounds = new ArrayList<>();

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;
}
