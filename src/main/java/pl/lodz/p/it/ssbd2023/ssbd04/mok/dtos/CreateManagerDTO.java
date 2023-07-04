package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CreateManagerDTO {
    AccountDTO accountData;
    @ToString.Exclude
    String password;
    ManagerDTO managerData;

    public CreateManagerDTO() {
    }

    public CreateManagerDTO(AccountDTO account, String password, ManagerDTO managerData) {
        this.accountData = account;
        this.password = password;
        this.managerData = managerData;
    }
}
