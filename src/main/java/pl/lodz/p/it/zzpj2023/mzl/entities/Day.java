package pl.lodz.p.it.zzpj2023.mzl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="days", indexes = {
        @Index(name = "day_round_fk", columnList = "round_id"),
        @Index(name = "day_timetable_fk", columnList = "timetable_id")
})
public class Day {
    @Id
    // @SequenceGenerator(name = "DaySeqGen", sequenceName = "DAY_PK_SEQ")
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DaySeqGen")
    @UuidGenerator
    @GeneratedValue
    @Column(name="id",nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name="date", nullable = false, updatable = false)
    @Getter @Setter
    private Date date;

    @OneToMany(mappedBy = "startTime")
    @Getter @Setter
    private List<Game> games = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false, updatable = false)
    private Round round;

    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false, updatable = false)
    private Timetable timetable;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    @Getter @Setter
    private long version;

}
