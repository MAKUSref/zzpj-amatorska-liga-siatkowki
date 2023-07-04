package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.League;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.AlgorithmConfigDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.GameDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TimetableDTO;

import java.util.Date;
import java.util.List;

public class Mzl18IntegarationTests extends TestUtils {

    AccountDTO admin = registerAccount(randomEmail(), randomLogin(), "ADMIN").as(AccountDTO.class);
    AccountDTO coach = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
    AccountDTO manager = registerAccount(randomEmail(), randomLogin(), "MANAGER").as(AccountDTO.class);
    AccountDTO captain = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);

    Date date = new Date();

    List<String> leagues = List.of(
            "123e4567-e89b-12d3-a456-426655440001",
            "223e4567-e89b-12d3-a456-426655440001",
            "323e4567-e89b-12d3-a456-426655440001"
    );

    List<String> venues = List.of(
            "123e4567-e89b-12d3-a456-426655440014",
            "223e4567-e89b-12d3-a456-426655440014",
            "323e4567-e89b-12d3-a456-426655440014"
    );

    TimetableDTO correctTimetableDTO = new TimetableDTO(
            new AlgorithmConfigDTO("Jan-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10) + " 00:00:00", "May-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10) + " 00:00:00"),
            List.of(leagues.get(0), leagues.get(1), leagues.get(2)),
            List.of(venues.get(0), venues.get(1), venues.get(2))
    );

    private Response timetableCreation(AccountDTO account, TimetableDTO timetableDTO) {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(account))
                .body(timetableDTO)
                .log().all()
                .post(timetableUri);
    }

    @Test
    void positiveGenerationOfTimetable() {
        Response response = timetableCreation(admin, correctTimetableDTO);
        Assertions.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    void notAdminRolesNotAllowed() {
        Response response1 = timetableCreation(coach, correctTimetableDTO);
        Assertions.assertEquals(response1.getStatusCode(), 403);
        Response response2 = timetableCreation(manager, correctTimetableDTO);
        Assertions.assertEquals(response2.getStatusCode(), 403);
        Response response3 = timetableCreation(captain, correctTimetableDTO);
        Assertions.assertEquals(response3.getStatusCode(), 403);
    }

    @Test
    void timetablesShouldNotBeenCreatedTwoTimesInSameTime() {
        var timetable1 = new TimetableDTO(
                new AlgorithmConfigDTO("Jan-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 1) + " 00:00:00", "May-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 1) + " 00:00:00"),
                List.of(leagues.get(0), leagues.get(1), leagues.get(2)),
                List.of(venues.get(0), venues.get(1), venues.get(2))
        );
        Response response1 = timetableCreation(admin, timetable1);
        Assertions.assertEquals(response1.getStatusCode(), 200);

        var timetable2 = new TimetableDTO(
                new AlgorithmConfigDTO("Jan-01-1973 00:00:00", "May-01-1973 00:00:00"),
                List.of(leagues.get(0), leagues.get(1), leagues.get(2)),
                List.of(venues.get(0), venues.get(1), venues.get(2))
        );
        Response response2 = timetableCreation(admin, timetable2);
        Assertions.assertEquals(response2.getStatusCode(), 400);
        Assertions.assertEquals(response2.getBody().asString(), "exception_timetable_dates_are_busy");
    }

    @Test
    void timetablesShouldNotBeenCreatedWhenStartDateMoreThenEndDate() {
        var timetable = new TimetableDTO(
                new AlgorithmConfigDTO("May-02-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00", "May-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00"),
                List.of(leagues.get(0), leagues.get(1), leagues.get(2)),
                List.of(venues.get(0), venues.get(1), venues.get(2))
        );
        Response response = timetableCreation(admin, timetable);
        Assertions.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getBody().asString(), "exception_timetable_start_date_after_end_date");
    }

    @Test
    void timetablesShouldNotBeenCreatedWhenNotEnoughFreeSpaceForTheGame() {
        var timetable = new TimetableDTO(
                new AlgorithmConfigDTO("Jan-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00", "Jan-03-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00"),
                List.of(leagues.get(0), leagues.get(1), leagues.get(2)),
                List.of(venues.get(0), venues.get(1), venues.get(2))
        );
        Response response = timetableCreation(admin, timetable);
        Assertions.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getBody().asString(), "exception_timetable_not_enough_free_space");
    }

    @Test
    void timetablesShouldNotBeenCreatedWhenLeagueIsTooSmall() {
        String league = createLeague();
        createTeamWithLeagueId(league);
        var timetable = new TimetableDTO(
                new AlgorithmConfigDTO("Jan-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00", "Jan-03-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00"),
                List.of(league),
                List.of(venues.get(0), venues.get(1), venues.get(2))
        );
        Response response = timetableCreation(admin, timetable);
        Assertions.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getBody().asString(), "exception_timetable_little_league");
    }

    @Test
    void timetablesShouldNotBeenCreatedWhenLeaguesIsEmpty() {
        var timetable = new TimetableDTO(
                new AlgorithmConfigDTO("Jan-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00", "May-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00"),
                List.of(),
                List.of(venues.get(0), venues.get(1), venues.get(2))
        );
        Response response = timetableCreation(admin, timetable);
        Assertions.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getBody().asString(), "exception_timetable_use_league");
    }

    @Test
    void timetablesShouldNotBeenCreatedWhenVenuesIsEmpty() {
        var timetable = new TimetableDTO(
                new AlgorithmConfigDTO("Jan-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00", "May-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 2) + " 00:00:00"),
                List.of(leagues.get(0), leagues.get(1), leagues.get(2)),
                List.of()
        );
        Response response = timetableCreation(admin, timetable);
        Assertions.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getBody().asString(), "exception_timetable_use_venue");
    }
}
