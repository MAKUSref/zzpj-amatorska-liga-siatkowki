package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class PasswordResetDTO extends AbstractDTO {

    @NotNull
    private UUID token;

    @NotNull
    @NotBlank
    @ToString.Exclude
    private String newPassword;


}
