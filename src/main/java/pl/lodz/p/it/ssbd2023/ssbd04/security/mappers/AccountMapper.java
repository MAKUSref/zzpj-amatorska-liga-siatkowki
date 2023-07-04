package pl.lodz.p.it.ssbd2023.ssbd04.security.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.entities.Role;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.RoleType;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd04.security.dtos.CreateTokenDTO;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public static CreateTokenDTO AccountToCreateTokenDTO(Account account) {

        List<RoleType> accountRoles = new ArrayList<>();
        accountRoles = account.getRoles().stream().map(Role::getRoleType).toList();
        return new CreateTokenDTO(accountRoles, account.getId());
    }

}
