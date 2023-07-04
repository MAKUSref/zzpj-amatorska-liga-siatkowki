package pl.lodz.p.it.ssbd2023.ssbd04.exceptions.mzl;

import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

public class TeamNotSubmittedException extends BaseApplicationException {
    public TeamNotSubmittedException() {
        super(Response.Status.NOT_ACCEPTABLE, exception_team_not_submitted);
    }

}
