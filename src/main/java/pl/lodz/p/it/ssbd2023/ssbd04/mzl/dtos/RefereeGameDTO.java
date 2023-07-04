package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.GameSquad;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefereeGameDTO extends AbstractDTO {
    String teamA;
    String teamB;
    String startTime;
    String endTime;
    int queue;
    boolean isScored;

    public RefereeGameDTO(GameSquad teamA, GameSquad teamB, String startTime, String endTime, int queue, boolean isScored, UUID id, long version) {
        super(id, version);
        this.teamA = teamA.getTeam().getTeamName();
        this.teamB = teamB.getTeam().getTeamName();
        this.startTime = startTime;
        this.endTime = endTime;
        this.queue = queue;
        this.isScored = isScored;
    }
}
