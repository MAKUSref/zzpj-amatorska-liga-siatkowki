package pl.lodz.p.it.zzpj2023.mok.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj2023.mzl.entities.Team;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleManagerDTO {

    @Size(min = 6, max = 30)
    private String username;

    @NotNull
    private Team team;
}
