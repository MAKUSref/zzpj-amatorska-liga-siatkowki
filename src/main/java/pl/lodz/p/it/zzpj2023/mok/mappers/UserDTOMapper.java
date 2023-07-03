package pl.lodz.p.it.zzpj2023.mok.mappers;

import pl.lodz.p.it.zzpj2023.mok.dtos.UserDTO;
import pl.lodz.p.it.zzpj2023.mok.entities.Account;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDTOMapper {
    public static UserDTO UserToDTO(Account account){
        return new UserDTO(account.getId(), account.getVersion(), account.getLogin(), account.getName(), account.getLastname(), account.getEmail(), account.isActive(), account.isApproved(), account.isBlocked(), account.getLoginTimestamp(), account.getLocale().toString());
    }

    public static Account DTOToUser(UserDTO user, String password){
        return new Account(user.getLogin(), password, user.getName(), user.getLastname(), user.getEmail(), user.isActive(), user.isApproved(), user.getLoginTimestamp(),new Locale(user.getLocale()) );
    }

    public static List<UserDTO> UsersToDTOList(List<Account> accounts) {
        return accounts == null ? null : accounts.stream()
                .filter(Objects::nonNull)
                .map(UserDTOMapper::UserToDTO)
                .collect(Collectors.toList());
    }

}
