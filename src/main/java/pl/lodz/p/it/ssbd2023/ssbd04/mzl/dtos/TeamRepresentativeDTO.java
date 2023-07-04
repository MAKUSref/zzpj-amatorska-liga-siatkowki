package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TeamRepresentativeDTO extends AbstractDTO {
    private String role;

    public TeamRepresentativeDTO() {
    }

    public TeamRepresentativeDTO(UUID id, long version, String role) {
        super(id, version);
        this.role = role;
    }
}