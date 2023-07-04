package pl.lodz.p.it.ssbd2023.ssbd04.mzl.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Manager;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Team;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.RepresentativeDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TeamWithRepresentativesDTO;

import java.util.ArrayList;
import java.util.List;

public class TeamWithRepresentativesMapper {


    public static TeamWithRepresentativesDTO teamToTeamWithRepresentativesDTO(Team t){
        RepresentativeDTO captain = null;
        RepresentativeDTO coach = null;
        if(t.getCaptain() != null) {
            captain = new RepresentativeDTO(t.getCaptain().getAccount().getId(), t.getCaptain().getId(), t.getCaptain().getAccount().getName(), t.getCaptain().getAccount().getLastname());
        }
        if(t.getCoach() != null) {
            coach = new RepresentativeDTO(t.getCoach().getAccount().getId(), t.getCoach().getId(), t.getCoach().getAccount().getName(), t.getCoach().getAccount().getLastname());
        }

        List<RepresentativeDTO> managers = new ArrayList<>();

        for(Manager m : t.getManager()){
            managers.add(new RepresentativeDTO(m.getAccount().getId(), m.getId(), m.getAccount().getName(), m.getAccount().getLastname()));
        }

        return new TeamWithRepresentativesDTO(t.getId(), t.getVersion(), t.getTeamName(), t.getCity(), t.isApproved(), t.getLeague().getLeagueNumber(), captain, coach, managers);
    }
}
