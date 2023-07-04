package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

public class AccountIsAlreadyManagerInThisTeam  extends BaseApplicationException {
    public AccountIsAlreadyManagerInThisTeam() {
        super(Response.Status.CONFLICT, exception_account_already_manager_in_this_team);
    }
}



