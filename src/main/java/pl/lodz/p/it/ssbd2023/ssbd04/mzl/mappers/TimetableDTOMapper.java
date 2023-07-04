package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Timetable;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.GetTimetableDTO;

import java.util.ArrayList;
import java.util.List;

public class TimetableDTOMapper {

    public static GetTimetableDTO timetableToDTO(Timetable timetable) {
        return new GetTimetableDTO(timetable.getId(),
            timetable.getVersion(),
            timetable.getStartDate(),
            timetable.getEndDate(),
            RoundDTOMapper.roundListToDTO(timetable.getRound())
        );
    }

    public static List<GetTimetableDTO> timetablesToDTO(List<Timetable> timetables) {
        List<GetTimetableDTO> timetablesDTO = new ArrayList<>();

        for (var timetable: timetables) {
            timetablesDTO.add(new GetTimetableDTO(timetable.getId(),
                timetable.getVersion(),
                timetable.getStartDate(),
                timetable.getEndDate(),
                RoundDTOMapper.roundListToDTO(timetable.getRound())
            ));
        }

        return timetablesDTO;
    }
}
