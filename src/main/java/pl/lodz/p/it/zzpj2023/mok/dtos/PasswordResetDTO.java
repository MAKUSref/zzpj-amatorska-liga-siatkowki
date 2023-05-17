package pl.lodz.p.it.zzpj2023.mok.dtos;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String token;
    private String newPassword;
}

