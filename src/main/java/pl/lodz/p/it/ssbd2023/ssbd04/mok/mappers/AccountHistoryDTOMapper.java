package pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers;

import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountHistoryDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.AccountHistory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountHistoryDTOMapper {

    public static AccountHistoryDTO AccountHistoryToDTO(AccountHistory accountHistory) {
        return new AccountHistoryDTO(
                accountHistory.getId(),
                accountHistory.getVersion(),
                AccountDTOMapper.AccountToDTO(accountHistory.getChangedBy()),
                AccountDTOMapper.AccountToDTO(accountHistory.getChangedAccount()),
                accountHistory.getChangedAt(),
                accountHistory.getChangeType(),
                accountHistory.getProperty()
        );
    }

    public static List<AccountHistoryDTO> AccountHistoriesToDTOList(List<AccountHistory> accountHistories) {
        return accountHistories == null ? null : accountHistories.stream()
                .filter(Objects::nonNull)
                .map(AccountHistoryDTOMapper::AccountHistoryToDTO)
                .collect(Collectors.toList());
    }
}
