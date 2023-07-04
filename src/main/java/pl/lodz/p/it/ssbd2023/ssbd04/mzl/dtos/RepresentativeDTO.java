package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RepresentativeDTO {

    UUID accountId;
    UUID roleId;
    String firstName;
    String lastName;

    public RepresentativeDTO(UUID accountId, UUID roleId, String firstName, String lastName) {
        this.accountId = accountId;
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public RepresentativeDTO() {
    }
}
