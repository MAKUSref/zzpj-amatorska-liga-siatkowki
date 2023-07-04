package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RemovePlayerFromTeamDTO {
    UUID teamId;
    UUID playerId;

    public RemovePlayerFromTeamDTO(UUID teamId, UUID playerId) {
        this.teamId = teamId;
        this.playerId = playerId;
    }

    public RemovePlayerFromTeamDTO() {
    }
}
