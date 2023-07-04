package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Set;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.SetDTO;

import java.util.ArrayList;
import java.util.List;

public class SetDTOMapper {
    public static SetDTO setToDTO (Set set) {
        return new SetDTO(set.getId(), set.getVersion(), set.getTeamAPoints(), set.getTeamBPoints());
    }

    public static Set setToDomain (SetDTO set) {
        return new Set(set.getId(), set.getTeamAPoints(), set.getTeamBPoints());
    }

    public static List<SetDTO> mapListToDTO (List<Set> sets) {
        List<SetDTO> setDTOS = new ArrayList<>();

        for (Set set: sets) {
            setDTOS.add(setToDTO(set));
        }
        return setDTOS;
    }

    public static List<Set> maplist(List<SetDTO> sets) {
        List<Set> setDTOS = new ArrayList<>();

        for (SetDTO set: sets) {
            setDTOS.add(setToDomain(set));
        }
        return setDTOS;
    }
}