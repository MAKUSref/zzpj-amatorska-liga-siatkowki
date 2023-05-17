package pl.lodz.p.it.zzpj2023.mok.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.zzpj2023.utils.etag.SignableEnt;

import java.util.*;

@Getter
@Setter
public class UserDTO extends AbstractDTO implements SignableEnt {
    private String login;
    private String name;
    private String lastname;
    private String email;
    private boolean isActive;
    private boolean isApproved;
    private boolean isBlocked;
    private Date loginTimestamp;
    private String locale;

public UserDTO() {
    }

    public UserDTO(UUID id, long version, String login, String name, String lastname, String email, boolean isActive, boolean isApproved, boolean isBlocked, Date loginTimestamp, String locale) {
        super(id, version);
        this.login = login;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.isActive = isActive;
        this.isApproved = isApproved;
        this.isBlocked = isBlocked;
        this.loginTimestamp = loginTimestamp;
        this.locale = locale;
    }

    @Override
    public String getPayload() {
        return login + getVersion();
    }
}
