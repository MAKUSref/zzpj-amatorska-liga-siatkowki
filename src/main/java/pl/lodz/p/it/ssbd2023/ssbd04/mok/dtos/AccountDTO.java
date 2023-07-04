package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import jakarta.json.bind.annotation.JsonbTransient;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.*;

@Getter
@Setter
public class AccountDTO extends AbstractDTO implements SignableEnt {
    private String login;
    private String name;
    private String lastname;
    private String email;
    private boolean isActive;
    private boolean isApproved;
    private boolean isBlocked;

    @JsonbTransient
    private Date loginTimestamp;
    private String locale;
    private List<RoleDTO> roles;

    public AccountDTO() {
    }

    public AccountDTO(
            UUID id, long version, String login,
            String name, String lastname, String email,
            boolean isActive, boolean isApproved,
            boolean isBlocked, Date loginTimestamp,
            String locale, List<RoleDTO> roles
    ) {
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
        this.roles = roles;
    }

    public AccountDTO(UUID id, long version, String login, String name, String lastname, String email, Date loginTimestamp, String locale) {
        super(id, version);
        this.login = login;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.loginTimestamp = loginTimestamp;
        this.locale = locale;
    }


    @Override
    public String getPayload() {
        return this.getId().toString() + getVersion();
    }
}
