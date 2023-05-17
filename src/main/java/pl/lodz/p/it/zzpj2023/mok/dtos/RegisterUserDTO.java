package pl.lodz.p.it.zzpj2023.mok.dtos;

import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
@Data
public class RegisterUserDTO {
    UserDTO userData;
    @ToString.Exclude
    String password;
    RoleDTO role;

    @Null
    UUID teamId;
    public RegisterUserDTO() {
    }

    public RegisterUserDTO(UserDTO user, String password, RoleDTO roleData) {
        this.userData = user;
        this.password = password;
        this.role = roleData;
    }
}
