package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO implements SignableEnt {
    public TeamDTO(UUID teamId, long teamVersion, String teamName, String city) {
        this.teamId = teamId;
        this.teamVersion = teamVersion;
        this.teamName = teamName;
        this.city = city;
    }

    public TeamDTO(UUID teamId, long teamVersion, String teamName, String city, List<PlayerDTO> players) {
        this.teamId = teamId;
        this.teamVersion = teamVersion;
        this.teamName = teamName;
        this.city = city;
        this.players = players;
    }

    @NotNull
    private UUID teamId;
    private long teamVersion;
    private String teamName;
    private String city;
    private List<PlayerDTO> players;
    private boolean isInLeague;
    private boolean isApproved;
    private int leagueNumber;

    public TeamDTO(UUID teamId, long teamVersion) {
        this.teamId = teamId;
        this.teamVersion = teamVersion;
    }

    @Override
    public String getPayload() {
        return this.getTeamId().toString() + this.getTeamVersion();
    }
}
