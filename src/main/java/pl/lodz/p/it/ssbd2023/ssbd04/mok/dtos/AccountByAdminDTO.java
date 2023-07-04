package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import lombok.*;

import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
public class AccountByAdminDTO extends AbstractDTO {

    @Getter
    @Setter
    private AccountDTO accountDto;

    @Getter
    @Setter
    private RoleDTO roleDTO;

    @Getter
    @Setter
    @ToString.Exclude
    private String password;

    public AccountByAdminDTO(UUID id, long version) {
        super(id, version);
    }

    public AccountByAdminDTO(){};
}
