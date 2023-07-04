package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ws.rs.core.Response;

public class GameException extends BaseApplicationException {
    protected final static String exception_gamesquads_set = "exception.gamesquads_set";
    protected final static String exception_same_teams = "exception.same_teams";

    protected final static String exception_self_postpone_approve = "exception.self_postpone_approve";

    protected  final static String exception_past_postpone_date = "exception.past_postpone_date";

    protected  final static String exception_game_already_finished = "exception.game_already_finished";

    protected  final static String exception_not_a_team_representative = "exception.not_a_team_representative";

    protected  final static String exception_invalid_role = "exception.invalid_role";

    public GameException(Response.Status status, String key) {
        super(status, key);
    }

    protected GameException(Response.Status status, String key, Throwable cause) {
        super(status, key, cause);
    }

    public static GameException bothGameSquadsAlreadySetException() {
        return new GameException(Response.Status.CONFLICT, exception_gamesquads_set);
    }

    public static GameException sameTeamsException() {
        return new GameException(Response.Status.CONFLICT, exception_same_teams);
    }

    public static GameException selfPostponeApproveException() {
        return new GameException(Response.Status.CONFLICT, exception_self_postpone_approve);
    }

    public static GameException pastPostponeDateException() {
        return new GameException(Response.Status.CONFLICT, exception_past_postpone_date);
    }

    public static GameException gameAlreadyFinishedException() {
        return new GameException(Response.Status.CONFLICT, exception_game_already_finished);
    }

    public static GameException notATeamRepresentativeException() {
        return new GameException(Response.Status.CONFLICT, exception_not_a_team_representative);
    }

    public static GameException invalidRoleException() {
        return new GameException(Response.Status.CONFLICT, exception_invalid_role);
    }

}
