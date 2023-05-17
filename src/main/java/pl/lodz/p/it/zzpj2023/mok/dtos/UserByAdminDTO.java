package pl.lodz.p.it.zzpj2023.mok.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class UserByAdminDTO extends AbstractDTO {

    @Getter
    @Setter
    private UserDTO accountDto;

    @Getter
    @Setter
    private RoleDTO roleDTO;

    @Getter
    @Setter
    @ToString.Exclude
    private String password;

    public UserByAdminDTO(UUID id, long version) {
        super(id, version);
    }

    public UserByAdminDTO(){};
}
