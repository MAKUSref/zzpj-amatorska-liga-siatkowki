package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;


@Getter
public class TeamWithRepresentativesDTO {

    private UUID teamId;
    private long teamVersion;
    private String teamName;
    private String city;
    private boolean inLeague;
    private int leagueNumber;

    private RepresentativeDTO captain;
    private RepresentativeDTO coach;
    private List<RepresentativeDTO> managers;

    public TeamWithRepresentativesDTO(UUID teamId, long teamVersion, String teamName, String city, boolean inLeague, int leagueNumber, RepresentativeDTO captain, RepresentativeDTO coach, List<RepresentativeDTO> managers) {
        this.teamId = teamId;
        this.teamVersion = teamVersion;
        this.teamName = teamName;
        this.city = city;
        this.inLeague = inLeague;
        this.leagueNumber = leagueNumber;
        this.captain = captain;
        this.coach = coach;
        this.managers = managers;
    }

    public TeamWithRepresentativesDTO() {
    }
}
