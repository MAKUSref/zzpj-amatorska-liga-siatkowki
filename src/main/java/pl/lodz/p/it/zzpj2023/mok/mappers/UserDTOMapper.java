package pl.lodz.p.it.zzpj2023.mok.mappers;

import pl.lodz.p.it.zzpj2023.mok.dtos.UserDTO;
import pl.lodz.p.it.zzpj2023.mok.entities.User;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDTOMapper {
    public static UserDTO UserToDTO(User user){
        return new UserDTO(user.getId(), user.getVersion(), user.getLogin(), user.getName(), user.getLastname(), user.getEmail(), user.isActive(), user.isApproved(), user.isBlocked(), user.getLoginTimestamp(), user.getLocale().toString());
    }

    public static User DTOToUser(UserDTO user, String password){
        return new User(user.getLogin(), password, user.getName(), user.getLastname(), user.getEmail(), user.isActive(), user.isApproved(), user.getLoginTimestamp(),new Locale(user.getLocale()) );
    }

    public static List<UserDTO> UsersToDTOList(List<User> users) {
        return users == null ? null : users.stream()
                .filter(Objects::nonNull)
                .map(UserDTOMapper::UserToDTO)
                .collect(Collectors.toList());
    }

}
