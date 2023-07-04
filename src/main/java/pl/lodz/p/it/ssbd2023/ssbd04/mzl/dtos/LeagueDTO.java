package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class LeagueDTO extends AbstractDTO {
    private List<UUID> rounds;
    private List<LeagueTeamDTO> teams;

    private int leagueNumber;

    @Pattern(regexp = "\\d{4}/\\d{4}", message = "Niepoprawny format. Oczekiwany format: rok/rok")
    private String season;

    public LeagueDTO() {}

    public LeagueDTO(UUID id, long version, int leagueNumber, String season) {
        super(id, version);
        this.leagueNumber = leagueNumber;
        this.season = season;
    }

    public LeagueDTO(UUID id, long version, List<UUID> rounds, List<LeagueTeamDTO> teams, int leagueNumber, String season) {
        super(id, version);
        this.rounds = rounds;
        this.teams = teams;
        this.leagueNumber = leagueNumber;
        this.season = season;
    }

    public LeagueDTO(UUID id, long version, List<LeagueTeamDTO> teams, int leagueNumber, String season) {
        super(id, version);
        this.teams = teams;
        this.leagueNumber = leagueNumber;
        this.season = season;
    }

}
