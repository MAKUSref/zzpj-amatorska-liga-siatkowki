package pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountDTOMapper {
    public static AccountDTO AccountToDTO(Account account){
        return new AccountDTO(
                account.getId(), account.getVersion(),
                account.getLogin(), account.getName(),
                account.getLastname(), account.getEmail(),
                account.isActive(), account.isApproved(),
                account.isBlocked(), account.getLoginTimestamp(),
                account.getLocale().toString(),
                RoleDTOMapper.roleListToRoleDTOList(account.getRoles())
        );
    }

    public static Account DTOToAccount(AccountDTO account, String password){
        return new Account(account.getLogin(), password, account.getName(), account.getLastname(), account.getEmail(), account.isActive(), account.isApproved(), account.getLoginTimestamp(),new Locale(account.getLocale()) );
    }

    public static List<AccountDTO> AccountsToDTOList(List<Account> accounts) {
        return accounts == null ? null : accounts.stream()
                .filter(Objects::nonNull)
                .map(AccountDTOMapper::AccountToDTO)
                .collect(Collectors.toList());
    }
}
