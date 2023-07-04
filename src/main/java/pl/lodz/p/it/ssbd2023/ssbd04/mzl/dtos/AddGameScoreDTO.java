package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddGameScoreDTO {
    List<SetDTO> sets;
    String gameId;
    int scoreboardPointsA;
    int scoreboardPointsB;
}
