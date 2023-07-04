package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.UUID;
import java.util.List;
@Getter @Setter
@NoArgsConstructor
public class ShowTeamDTO extends AbstractDTO {
    private String teamId;
    private String teamName;
    private String city;
    private TeamCaptainDTO captain;
    private List<TeamManagerDTO> managers;
    private TeamCoachDTO coach;
    private boolean isInLeague;
    private int leagueNumber;
    private boolean isApproved;

    public ShowTeamDTO(UUID id, long version, String teamName, String city) {
        super(id, version);
        this.teamName = teamName;
        this.city = city;
    }

}
