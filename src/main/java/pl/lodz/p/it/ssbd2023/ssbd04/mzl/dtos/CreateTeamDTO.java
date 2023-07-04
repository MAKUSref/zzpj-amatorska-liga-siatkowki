package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamDTO {
    @NotNull
    @NotEmpty
    private String teamName;

    @NotNull
    @Size
    private String city;
}
