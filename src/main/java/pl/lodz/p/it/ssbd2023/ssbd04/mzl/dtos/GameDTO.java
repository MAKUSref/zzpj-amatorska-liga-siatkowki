package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO extends AbstractDTO implements SignableEnt {
    VenueDTO venue;
    GameSquadDTO teamA;
    GameSquadDTO teamB;
    String referee;
    ScoreDTO score;
    String startTime;
    String endTime;
    int queue;
    boolean isPostponed;
    String postponingAccount;
    String postponeDate;
    public GameDTO(UUID id, long version) {
        super(id, version);
    }
    public GameDTO(UUID id, long version, VenueDTO venue, GameSquadDTO teamA, GameSquadDTO teamB, String referee, ScoreDTO score, String startTime, String endTime, int queue) {
        super(id, version);
        this.venue = venue;
        this.teamA = teamA;
        this.teamB = teamB;
        this.referee = referee;
        this.score = score;
        this.startTime = startTime;
        this.endTime = endTime;
        this.queue = queue;
    }

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }

    public GameDTO(UUID id, long version, VenueDTO venue, GameSquadDTO teamA, GameSquadDTO teamB, String referee, ScoreDTO score, String startTime, String endTime, int queue, boolean isPostponed, String postponingAccount, String postponeDate) {
        super(id, version);
        this.venue = venue;
        this.teamA = teamA;
        this.teamB = teamB;
        this.referee = referee;
        this.score = score;
        this.startTime = startTime;
        this.endTime = endTime;
        this.queue = queue;
        this.isPostponed = isPostponed;
        this.postponingAccount = postponingAccount;
        this.postponeDate = postponeDate;
    }
}
