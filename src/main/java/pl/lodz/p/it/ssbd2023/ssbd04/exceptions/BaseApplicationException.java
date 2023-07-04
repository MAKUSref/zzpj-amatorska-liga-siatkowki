package pl.lodz.p.it.ssbd2023.ssbd04.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@ApplicationException(rollback = true)
public class BaseApplicationException extends WebApplicationException {

    protected final static String exception_unknown = "exception.unknown";
    protected final static String exception_general_persistence = "exception.general_persistence";
    protected final static String exception_entity_not_found = "exception.entity_not_found";
    protected final static String exception_optimistic_lock = "exception.optimistic_lock";
    protected final static String exception_access_denied = "exception.access_denied";
    protected final static String exception_account_not_found = "exception.account_not_found";
    protected final static String exception_account_does_not_have_any_teams = "exception.account_does_not_have_any_teams";
    protected final static String exception_account_already_has_a_team = "exception.account_already_has_a_team";
    protected final static String exception_league_not_found = "exception.league_not_found";
    protected final static String exception_team_is_already_in_league = "exception.team_is_already_in_league";
    protected final static String exception_team_not_submitted = "exception.team_is_not_submitted";
    protected final static String exception_team_not_found = "exception.team_not_found";
    protected final static String exception_team_already_exists = "exception.team_already_exists";
    protected final static String exception_transaction_rollback = "exception.transaction_rollback";
    protected final static String exception_unauthorized = "exception.unauthorized";
    protected final static String exception_token_expired = "exception.token_expired";
    protected final static String exception_account_not_approved = "exception.account_not_approved";
    protected final static String exception_language_not_supported = "exception.language_not_supported";
    protected final static String exception_invalid_token = "exception.invalid_token";
    protected final static String exception_not_found = "exception.not_found";
    protected final static String exception_etag = "exception.etag";
    protected final static String exception_entity_integrity = "exception.entity_integrity";
    protected final static String exception_forbidden_access = "exception.forbidden_access";
    protected final static String exception_account_inactive = "exception.account_inactive";
    protected final static String exception_account_has_that_role = "exception.account_has_that_role";
    protected final static String exception_min_account_role = "exception.min_account_role";
    protected final static String exception_account_role_not_found = "exception.account_role_not_found";
    protected final static String exception_role_not_allowed = "exception.role_not_allowed";
    protected final static String exception_self_blocking = "exception.cannot_self_blocked";
    protected final static String exception_incorrect_role = "exception.incorrect_role";
    protected final static String exception_already_blocked = "exception.already_blocked";
    protected final static String exception_already_unblocked = "exception.already_unblocked";
    protected final static String exception_passwords_do_not_match = "exception.incorrect_old_password";
    protected final static String exception_method_not_implemented = "exception.method_not_implemented";
    protected final static String exception_transaction_required = "exception.transaction_required";
    protected final static String exception_general_ejb = "exception.general_ejb";
    protected final static String exception_team_already_has_captain = "exception_team_already_has_captain";
    protected final static String exception_team_already_has_coach = "exception_team_already_has_coach";
    protected final static String exception_team_not_play_in_game = "exception_team_not_play_in_game";
    protected final static String exception_timetable_start_date_after_end_date = "exception_timetable_start_date_after_end_date";
    protected final static String exception_timetable_not_enough_free_space = "exception_timetable_not_enough_free_space";
    protected final static String exception_timetable_little_league = "exception_timetable_little_league";
    protected final static String exception_timetable_dates_are_busy = "exception_timetable_dates_are_busy";
    protected final static String exception_timetable_use_league = "exception_timetable_use_league";
    protected final static String exception_timetable_use_venue = "exception_timetable_use_venue";
    protected final static String exception_role_occupied = "exception.role_occupied";
    protected final static String exception_role_empty = "exception.role_empty";
    protected final static String exception_account_already_manager_in_this_team = "exception.account_already_manager_in_this_team";
    protected final static String exception_incorrect_representative = "exception_incorrect_representative";
    protected final static String exception_player_not_in_team = "exception.player_not_in_team";
    protected final static String exception_player_in_game_squad = "exception.player_in_game_squad";

    @Getter
    private Throwable cause;

    protected BaseApplicationException(Response.Status status, String key, Throwable cause) {
        super(Response.status(status).entity(key).build());
        this.cause = cause;
    }

    protected BaseApplicationException(Response.Status status, String key) {
        super(Response.status(status).entity(key).build());
    }

    // Wyjątek ogólny opakowuje nie obsługiwane inaczej typy wyjątków
    public static BaseApplicationException createGeneralErrorException(Throwable cause) {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_unknown, cause);
    }

    public static BaseApplicationException createGeneralErrorException(String key, Throwable cause) {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, key, cause);
    }

    public static BaseApplicationException createGeneralPersistenceException(Exception cause) {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_general_persistence, cause);
    }

    public static BaseApplicationException createNoAccessException() {
        return new BaseApplicationException(Response.Status.FORBIDDEN, exception_access_denied);
    }

    public static BaseApplicationException createCustomConstraintException(String msg) {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, msg);
    }

    public static BaseApplicationException createTransactionRollbackException() {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_transaction_rollback);
    }

    public static BaseApplicationException createUnauthorizedException() {
        return new BaseApplicationException(Response.Status.UNAUTHORIZED, exception_unauthorized);
    }

    public static BaseApplicationException InvalidETagException() {
        return new BaseApplicationException(Response.Status.PRECONDITION_FAILED, exception_etag);
    }

    public static BaseApplicationException EntityIntegrityException() {
        return new BaseApplicationException(Response.Status.PRECONDITION_FAILED, exception_entity_integrity);
    }

    public static BaseApplicationException incorrectRole() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_incorrect_role);
    }

    public static BaseApplicationException accountAlreadyBlocked() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_already_blocked);
    }
    public static BaseApplicationException accountAlreadyUnblocked() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_already_unblocked);
    }

    public static BaseApplicationException passwordsDoNotMatch() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_passwords_do_not_match);
    }

    public static BaseApplicationException transactionRequired() {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_transaction_required);
    }

    public static BaseApplicationException generalEJBException() {
        return new BaseApplicationException(Response.Status.INTERNAL_SERVER_ERROR, exception_general_ejb);
    }

    public static BaseApplicationException timetableStartDateAfterEndDate() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_timetable_start_date_after_end_date);
    }

    public static BaseApplicationException timetableNotEnoughFreeSpace() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_timetable_not_enough_free_space);
    }

    public static BaseApplicationException timetableUseLeague() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_timetable_use_league);
    }

    public static BaseApplicationException timetableUseVenue() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_timetable_use_venue);
    }

    public static BaseApplicationException timetableLittleLeague() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_timetable_little_league);
    }

    public static BaseApplicationException timetableDatesAreBusy() {
        return new BaseApplicationException(Response.Status.BAD_REQUEST, exception_timetable_dates_are_busy);
    }

    public static AppNoEntityException createNoEntityException() {
        return new AppNoEntityException();
    }

    public static AppOptimisticLockException createOptimisticLockException() {
        return new AppOptimisticLockException();
    }

}
