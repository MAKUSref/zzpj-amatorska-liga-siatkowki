package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Getter @Setter
public class LeagueTeamDTO extends AbstractDTO implements SignableEnt {
    private String teamName;
    private String city;

    public LeagueTeamDTO() {}

    public LeagueTeamDTO(UUID id, long version, String teamName, String city) {
        super(id, version);
        this.teamName = teamName;
        this.city = city;
    }

    @Override
    public String getPayload() {
        return this.getId().toString() + getVersion();
    }
}
