package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;
import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDTO extends AbstractDTO {
    private List<SetDTO> sets;
    private int scoreboardPointsA;
    private int scoreboardPointsB;
    private String approvalTeamA;
    private String approvalTeamB;

    public ScoreDTO(UUID id, long version, List<SetDTO> sets, int scoreboardPointsA, int scoreboardPointsB, String approvalTeamA, String approvalTeamB) {
        super(id, version);
        this.sets = sets;
        this.scoreboardPointsA = scoreboardPointsA;
        this.scoreboardPointsB = scoreboardPointsB;
        this.approvalTeamA = approvalTeamA;
        this.approvalTeamB = approvalTeamB;
    }
}
