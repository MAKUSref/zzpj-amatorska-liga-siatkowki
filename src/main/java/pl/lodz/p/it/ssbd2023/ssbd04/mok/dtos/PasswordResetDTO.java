package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String token;
    private String newPassword;
}

