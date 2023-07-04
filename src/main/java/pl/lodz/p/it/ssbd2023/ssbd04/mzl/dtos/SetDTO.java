package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetDTO extends AbstractDTO {
    private int teamAPoints;
    private int teamBPoints;

    public SetDTO(UUID id, long version, int teamAPoints, int teamBPoints) {
        super(id, version);
        this.teamAPoints = teamAPoints;
        this.teamBPoints = teamBPoints;
    }
}
