package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountHistoryDTO extends AbstractDTO {

    private AccountDTO changedBy;

    @NotNull
    private AccountDTO changedAccount;

    private Date changedAt;

    private String changeType;

    private String property;

    public AccountHistoryDTO(UUID id, long version, AccountDTO changedBy, AccountDTO changedAccount, Date changedAt, String changeType, String property) {
        super(id, version);
        this.changedBy = changedBy;
        this.changedAccount = changedAccount;
        this.changedAt = changedAt;
        this.changeType = changeType;
        this.property = property;
    }
}
