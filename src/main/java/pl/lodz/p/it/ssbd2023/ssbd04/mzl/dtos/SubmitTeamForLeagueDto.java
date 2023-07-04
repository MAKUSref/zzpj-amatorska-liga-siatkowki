package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTeamForLeagueDto extends AbstractDTO {

    @NotNull
    private String teamId;

    @NotNull
    private String leagueId;
}
