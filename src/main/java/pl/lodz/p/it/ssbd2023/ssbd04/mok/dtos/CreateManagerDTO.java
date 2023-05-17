package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CreateManagerDTO {
    UserDTO userData;
    @ToString.Exclude
    String password;
    ManagerDTO managerData;

    public CreateManagerDTO() {
    }

    public CreateManagerDTO(UserDTO user, String password, ManagerDTO managerData) {
        this.userData = user;
        this.password = password;
        this.managerData = managerData;
    }
}
