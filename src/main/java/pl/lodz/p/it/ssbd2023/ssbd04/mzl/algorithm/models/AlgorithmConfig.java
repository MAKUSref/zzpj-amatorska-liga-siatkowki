package pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TimetableDTO;

import java.sql.Time;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlgorithmConfig {
    private Date startDate;

    private Date endDate;

    private List<String> expectedDaysFilterValues = Arrays.asList("Friday", "Saturday", "Sunday");

    private Time roundMaxTime = new Time(1, 0, 0);

    private Time dayStart = new Time(9, 0, 0);

    private Time dayEnd = new Time(22, 0, 0);
}
