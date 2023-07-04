package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.AlgorithmConfigDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.GetTimetableDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TimetableDTO;

import java.util.Date;
import java.util.List;

public class Mzl1GetTimetable extends TestUtils {

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
            new AlgorithmConfigDTO("Jan-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 6) + " 00:00:00", "Nov-01-" + (3000 + date.getHours() * 100 + date.getMinutes() * 10 + 7) + " 00:00:00"),
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

    private Response getTimetables() {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .log().all()
                .get(timetableUri);
    }


    @Test
    void positiveGenerationOfTimetable() {
        Response response = timetableCreation(admin, correctTimetableDTO);
        Assertions.assertEquals(200, response.getStatusCode());}
    private String getTeamIdFromCreateTeamResponse(Response response) {
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> idList = jsonPathEvaluator.getList("id");
        if (!idList.isEmpty()) {
            return idList.get(0);
        }
        return null;
    }
    String id =  getTeamIdFromCreateTeamResponse(getTimetables());
    private Response getTimetable() {
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .log().all()
                .get(timetableUri + "/" + id);
    }
    @Test
    void positiveTestGetTimetable() {
        Response response = getTimetable();
        Assertions.assertEquals(200, response.getStatusCode());
    }
}
