package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
@Data
public class RegisterAccountDTO {
    AccountDTO accountData;

    @Size(min=8, max=64)
    @ToString.Exclude
    String password;
    RoleDTO role;

    @Null
    UUID teamId;
    public RegisterAccountDTO() {
    }

    public RegisterAccountDTO(AccountDTO account, String password, RoleDTO roleData) {
        this.accountData = account;
        this.password = password;
        this.role = roleData;
    }
}
