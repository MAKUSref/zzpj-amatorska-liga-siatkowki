package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.Locale;
import java.util.UUID;

@Getter
@Setter
public class EditAccountLanguageDTO extends AbstractDTO implements SignableEnt {
    private String login;
    private String locale;

    public EditAccountLanguageDTO(UUID id, long version, String login, String locale) {
        super(id, version);
        this.login = login;
        this.locale = locale;
    }

    public EditAccountLanguageDTO(){}

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }

}
