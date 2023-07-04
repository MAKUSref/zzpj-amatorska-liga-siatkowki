package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RegisterAccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Role;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.RoleType;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers.RoleDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.security.JWTManager;
import pl.lodz.p.it.ssbd2023.ssbd04.security.dtos.CreateTokenDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

import static java.lang.Math.random;

public class TestUtils {

    public static String baseUri;
    public static String teamUri;

    public static String leagueUri;
    public static String gameUri;
    public static String playerUri;
    public static String scoreboardUri;
    public static String timetableUri;

    public static RequestSpecification requestSpecification;
    public static String adminUUID = "d93b7ab9-da9b-490c-a096-5c48437cd3a8";
    public static String managerUUID = "9d4245e4-d90e-43f2-a9b3-9b5c5e68f8a7";
    public static String refereeUUID = "114245e4-d90e-43f2-a9b3-9b5c5e68f8a7";

    @BeforeAll
    static void init() {
        System.setProperty("jwt.duration", "30");
        baseUri = "https://localhost:8181/api/account";
        teamUri = "https://localhost:8181/api/team";
        leagueUri = "https://localhost:8181/api/league";
        gameUri = "https://localhost:8181/api/game";
        playerUri = "https://localhost:8181/api/player";
        scoreboardUri = "https://localhost:8181/api/scoreboard";
        timetableUri = "https://localhost:8181/api/timetable";
        requestSpecification = new RequestSpecBuilder().setBaseUri(baseUri).build();
        if (!isAdminInDb()) {
//            createReferee();
            createAdmin();
            createManager();
        }

        RestAssured.defaultParser = Parser.JSON;
    }

    public static AccountDTO getAccount(String id) {
        Response response = RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get("/getAccountByUUID/" + id);
        if (response.getStatusCode() == 200) {
            return response.getBody().as(AccountDTO.class);
        } else {
            return null;
        }

    }

    public static TeamDTO getTeam(String id) {
        Response response = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(teamUri + "/getTeamById/" + id);
        if (response.getStatusCode() == 200) {
            return response.getBody().as(TeamDTO.class);
        } else {
            System.out.println(response.getBody().print());

            return null;
        }
    }


    public static TeamDTO getTeamWithPlayers(String id) {
        Response response = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(teamUri + "/getById/" + id);
        if (response.getStatusCode() == 200) {
            return response.getBody().as(TeamDTO.class);
        } else {
            System.out.println(response.getBody().print());

            return null;
        }
    }

    public static TeamWithRepresentativesDTO getTeamWithRepresentatives(String id) {
        Response response = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(teamUri + "/getTeamWithRepresentatives/" + id);
        if (response.getStatusCode() == 200) {
            return response.getBody().as(TeamWithRepresentativesDTO.class);
        } else {
            return null;
        }
    }
    @SneakyThrows
    public List<AccountDTO> getAccountList() {
        Response response = RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get("");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(
                response.getBody().asString(),
                new TypeReference<>() {
                }
        );
    }

    public static String randomEmail() {
        Random random = new Random();
        return "donotsend" + generateRandomString(random.nextInt(10) + 5) + "@" + generateRandomString(random.nextInt(5) + 3) + "." + generateRandomString(random.nextInt(1) + 3);
    }

    public static String randomLogin() {
        return generateRandomString(10);
    }

    public static String generateRandomString(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static int randomNumber() {
        Random random = new Random();
        return random.nextInt(9999999);

    }

    @SneakyThrows
    public static String createTeam() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', true, ?, 0, null, null, null);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, randomLogin());
            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createTeamWithVersion() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', true, ?, 0, null, null, null);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, randomLogin());
            preparedStatement.executeUpdate();
            return uuid;
        }
    }
    @SneakyThrows
    public static String createTeamWithLeagueId(String leagueId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', true, ?, 0, null, null, UUID(?));";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, randomLogin());
            preparedStatement.setString(3, leagueId);
            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static void createAdmin(){
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('d93b7ab9-da9b-490c-a096-5c48437cd3a8', 0, 'TESTadminMZL@mail.com', true, true, false, 'TESTadminMZL', 'en', 'TESTadminMZL', '2023-05-18 18:53:32.000000', 'TESTadminMZL', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G');\n" +
                    "INSERT INTO public.roles (id, version, role_type, account_id) VALUES ('4570ecea-3f89-41e3-9574-f4890b2e593f', 0, 'ADMIN', 'd93b7ab9-da9b-490c-a096-5c48437cd3a8');\n" +
                    "INSERT INTO public.admins (id) VALUES ('4570ecea-3f89-41e3-9574-f4890b2e593f');";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public static void createManager(){
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9d4245e4-d90e-43f2-a9b3-9b5c5e68f8a7', 0, 'TESTmanagerMZL@mail.com', true, true, false, 'TESTmanagerMZL', 'en', 'TESTmanagerMZL', '2023-05-18 18:53:32.000000', 'TESTmanagerMZL', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G');\n" +
                    "INSERT INTO public.roles (id, version, role_type, account_id) VALUES ('29811b1e-5021-4a3e-9568-c6d76c80764f', 0, 'MANAGER', '9d4245e4-d90e-43f2-a9b3-9b5c5e68f8a7');\n" +
                    "INSERT INTO public.admins (id) VALUES ('29811b1e-5021-4a3e-9568-c6d76c80764f');";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public static String createGame(String refereeId, String gamesquadAId, String gamesquadBId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();

        String scoreId = createScore();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.games(id,version,queue,start_time,referee_id,score_id,teamagamesquad,teambgamesquad, venue_id, is_postponed, is_postpone_request)VALUES(UUID(?),0,1,'2023-06-09T14:40:48+00:00',UUID(?),UUID(?),UUID(?), UUID(?),UUID(?), false, false);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, refereeId);
            preparedStatement.setString(3, scoreId);
            preparedStatement.setString(4, gamesquadAId);
            preparedStatement.setString(5, gamesquadBId);
            preparedStatement.setString(6, createVenue());

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    private static String createScore() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.scores(id,version,approvalteama, approvalteamb, scoreboard_points_a, scoreboard_points_b )VALUES(UUID(?),0, ?, ?, 0, 0);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setObject(2, "NONE");
            preparedStatement.setObject(3, "NONE");


            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createTimetable() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();


        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.timetables(id,version,start_date, end_date)VALUES(UUID(?),0,'2023-06-09T14:40:48+00:00','2023-06-10T14:40:48+00:00');";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    public static GameDTO getGame(String id) {
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(gameUri + "/getGameById/" + id);
        if (response.getStatusCode() < 400) {
            return response.getBody().as(GameDTO.class);
        } else {
            return null;
        }
    }

    public static String getGameETag(String gameId){
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(gameUri + "/getGameById/" + gameId);
        if (response.getStatusCode() == 200) {
            return response.getHeader("ETAG");
        } else {
            return null;
        }
    }

    public static String getTeamETag(String teamId){
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(teamUri + "/" + teamId);
        if (response.getStatusCode() == 200) {
            return response.getHeader("ETAG");
        } else {
            return null;
        }
    }

    @SneakyThrows
    public static String createGamesquad(String teamId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();


        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.game_squads(id,version,team_id)VALUES(UUID(?),0,UUID(?));";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, teamId);

            preparedStatement.executeUpdate();
            return uuid;
        }
    }
    @SneakyThrows
    public static GameDTO createMzl10Game(String refereeId, String gameSquadA, String gameSquadB) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();
        long version = 10;
        String scoreId = createUnacceptedScore(20,25);
        String roundId = createLeagueWithRound().roundId;
        GameDTO gameDTO = new GameDTO(UUID.fromString(uuid), version);
        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.games (id, end_time, is_postponed, is_postpone_request, queue, start_time, version, referee_id, score_id, teamagamesquad, teambgamesquad, venue_id, round_id) VALUES (UUID(?), null , false, false,  1, '2023-07-01 14:30:00', CAST(? AS BIGINT), UUID(?), UUID(?), UUID(?), UUID(?), UUID(?), UUID(?))";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, String.valueOf(version));
            preparedStatement.setString(3, refereeId);
            preparedStatement.setString(4, scoreId);
            preparedStatement.setString(5, gameSquadA);
            preparedStatement.setString(6, gameSquadB);
            preparedStatement.setString(7, createVenue());
            preparedStatement.setString(8, roundId);


            preparedStatement.executeUpdate();
            return gameDTO;
        }
    }

    @SneakyThrows
    public static String createVenue() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.venues(id,version,court_number,address)VALUES(UUID(?),0,(?),'piotrkowska');";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setObject(1, uuid);
            preparedStatement.setInt(2, randomNumber());
            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static List<String> createPlayer(int number, String teamid) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        List<String> playerIds = new ArrayList<>();
        String uuid = UUID.randomUUID().toString();


        for (int i = 0; i < number; i++) {

            try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
                String sqlQuery = "INSERT INTO public.players(id,version,last_name,first_name,is_pro,age,team_id)VALUES(UUID(?),0,'testowy','tester',false,24,UUID(?));";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, teamid);
                preparedStatement.executeUpdate();
                playerIds.add(uuid);
                uuid = UUID.randomUUID().toString();

            }
        }
        return playerIds;
    }


    @SneakyThrows
    public static CreatePlayerDTO createAddPlayerToTeamdDTO() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery5 = """
            DELETE FROM teams WHERE id = '223e4567-e89b-12d3-a456-426655440010';""";
            PreparedStatement preparedStatement5 = connection.prepareStatement(sqlQuery5);
            preparedStatement5.executeUpdate();

            String sqlQuery2 = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('223e4567-e89b-12d3-a456-426655440010', 'City 1', true, 'Team 2', 1, NULL, NULL, NULL);\n";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);

            preparedStatement2.executeUpdate();

            TeamDTO teamDTO = new TeamDTO(UUID.fromString("223e4567-e89b-12d3-a456-426655440010"),1);
            CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO("Mateusz", "Kaczmarski",21, true, teamDTO);

            return createPlayerDTO;
        }
    }
    @SneakyThrows
    public static String createReferee() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String refereeId = UUID.randomUUID().toString();
        String roleId = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery1 = "INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES (UUID(?), 0, ?, true, true, false, 'referee', 'en', ?, '2023-05-18 18:53:32.000000', 'referee', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G');\n";
            String sqlQuery2 = "INSERT INTO public.roles (id, version, role_type, account_id) VALUES (UUID(?), 0, 'REFEREE', UUID(?));\n";
            String sqlQuery3 = "INSERT INTO public.referees (id) VALUES (UUID(?));\n";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
            PreparedStatement preparedStatement3 = connection.prepareStatement(sqlQuery3);

            preparedStatement1.setObject(1, refereeId);
            preparedStatement1.setObject(2, randomEmail());
            preparedStatement1.setObject(3, randomLogin());
            preparedStatement2.setObject(1, roleId);
            preparedStatement2.setObject(2, refereeId);
            preparedStatement3.setObject(1, roleId);

            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();

            return refereeId;
        }
    }

    @SneakyThrows
    public static String createRefGetRoleId() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String refereeId = UUID.randomUUID().toString();
        String roleId = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery1 = "INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES (UUID(?), 0, ?, true, true, false, 'referee', 'en', ?, '2023-05-18 18:53:32.000000', 'referee', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G');\n";
            String sqlQuery2 = "INSERT INTO public.roles (id, version, role_type, account_id) VALUES (UUID(?), 0, 'REFEREE', UUID(?));\n";
            String sqlQuery3 = "INSERT INTO public.referees (id) VALUES (UUID(?));\n";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
            PreparedStatement preparedStatement3 = connection.prepareStatement(sqlQuery3);

            preparedStatement1.setObject(1, refereeId);
            preparedStatement1.setObject(2, randomEmail());
            preparedStatement1.setObject(3, randomLogin());
            preparedStatement2.setObject(1, roleId);
            preparedStatement2.setObject(2, refereeId);
            preparedStatement3.setObject(1, roleId);

            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();

            return roleId;
        }
    }
    @SneakyThrows
    public static String createGame(String refereeId, String gamesquadAId, String gamesquadBId, String roundId, String scoreId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();


        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO games (id, end_time, is_postponed, is_postpone_request, queue, start_time, version, referee_id, score_id, teamAGameSquad, teamBGameSquad, venue_id, round_id) VALUES (UUID(?), '2023-06-01 15:00:00', 'false', 'false',  1, '2023-06-01 14:30:00', 1, UUID(?), UUID(?), UUID(?), UUID(?), UUID(?), UUID(?));";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, refereeId);
            preparedStatement.setString(3, scoreId);
            preparedStatement.setString(4, gamesquadAId);
            preparedStatement.setString(5, gamesquadBId);
            preparedStatement.setString(6, createVenue());
            preparedStatement.setString(7, roundId);

            preparedStatement.executeUpdate();
            return uuid;
        }
    }
    @SneakyThrows
    public static String createLeague() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.leagues (id, version, league_number, season) VALUES (UUID(?), 0, 1, '2022/2023');";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setObject(1, uuid);
            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @AllArgsConstructor
    public static class CreateLeagueWithRoundParams {
        public String leagueId;
        public String roundId;
        public String scoreboardId;
    }
    @SneakyThrows
    public static CreateLeagueWithRoundParams createLeagueWithRound() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String leagueId = UUID.randomUUID().toString();
        String roundId = UUID.randomUUID().toString();
        String scoreboardId = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery1 = "INSERT INTO public.leagues (id, version, league_number, season) VALUES (UUID(?), 0, 1, '2022/2023');";
            String sqlQuery2 = "INSERT INTO scoreboards (id, version) VALUES (UUID(?), 1);";
            String sqlQuery3 = "INSERT INTO rounds (id, version, scoreboard_id, timetable_id, round_number) VALUES (UUID(?), 1, UUID(?), UUID(?), 1);";
            String sqlQuery4 = "INSERT INTO leagues_rounds (round_id, league_id) VALUES (UUID(?), UUID(?));";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery1);
            preparedStatement1.setObject(1, leagueId);

            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
            preparedStatement2.setObject(1, scoreboardId);

            PreparedStatement preparedStatement3 = connection.prepareStatement(sqlQuery3);
            preparedStatement3.setObject(1, roundId);
            preparedStatement3.setObject(2, scoreboardId);
            preparedStatement3.setObject(3, createTimetable());

            PreparedStatement preparedStatement4 = connection.prepareStatement(sqlQuery4);

            preparedStatement4.setObject(1, roundId);
            preparedStatement4.setObject(2, leagueId);
            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();
            preparedStatement4.executeUpdate();
            return new CreateLeagueWithRoundParams(leagueId, roundId, scoreboardId);
        }
    }

    @SneakyThrows
    public static String createApprovedTeam() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();
        String teamName = generateRandomString(6);

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', true, ?, 0, null, null, null);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, teamName);
            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createUnapprovedTeam() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();
        String teamName = generateRandomString(6);

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', false, ?, 0, null, null, null);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, teamName);
            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createCaptain(String email, String login) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String accountId = UUID.randomUUID().toString();
        String roleId = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery1 = "INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES (UUID(?), 0, ?, true, true, false, 'captain', 'en', ?, '2023-05-18 18:53:32.000000', 'captain', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G')";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery1);
            preparedStatement.setString(1, accountId);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, login);
            preparedStatement.executeUpdate();

            String sqlQuery2 = "INSERT INTO public.roles (id, version, role_type, account_id) VALUES (UUID(?), 0, 'CAPTAIN', UUID(?))";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
            preparedStatement2.setString(1, roleId);
            preparedStatement2.setString(2, accountId);
            preparedStatement2.executeUpdate();

            String sqlQuery3 = "INSERT INTO public.captains (id) VALUES (UUID(?))";
            PreparedStatement preparedStatement3 = connection.prepareStatement(sqlQuery3);
            preparedStatement3.setString(1, roleId);
            preparedStatement3.executeUpdate();
            return roleId;
        }
    }

    @SneakyThrows
    public static String createAccount() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String accountId = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery1 = "INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES (UUID(?), 0, ?, true, true, false, 'captain', 'en', ?, '2023-05-18 18:53:32.000000', 'captain', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G')";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery1);
            preparedStatement.setString(1, accountId);
            preparedStatement.setString(2, randomEmail());
            preparedStatement.setString(3, randomLogin());
            preparedStatement.executeUpdate();
        }
        return accountId;
    }

    @SneakyThrows
    public static String createMzl6Team(String captainId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();
        String teamName = generateRandomString(5);

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', true, ?, 0, UUID(?), null, null);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, teamName);
            preparedStatement.setString(3, captainId);
            preparedStatement.executeUpdate();
            return uuid;
        }
    }
    @SneakyThrows
    public static TeamDTO createMzl10TeamWithCaptain(String captainId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();
        String teamName = generateRandomString(5);
        long version = 0;

        TeamDTO teamDTO = new TeamDTO(UUID.fromString(uuid), 0);
        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', true, ?, CAST(? AS BIGINT), UUID(?), null, null);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, teamName);
            preparedStatement.setString(3, String.valueOf(version));
            preparedStatement.setString(4, captainId);
            preparedStatement.executeUpdate();
            return teamDTO;
        }
    }
    @SneakyThrows
    public static String createMzl6TeamWithCoach(String coach) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();
        String teamName = generateRandomString(5);

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', true, ?, 0, null, UUID(?), null);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, teamName);
            preparedStatement.setString(3, coach);
            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createMzl6GameSquad(String teamId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.game_squads (id, version, team_id) VALUES (UUID(?), 0, UUID(?))";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, teamId);
            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createMzl6Game(String refereeId, String gameSquadA, String gameSquadB) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.games (id, end_time, is_postponed, is_postpone_request, queue, start_time, version, referee_id, score_id, teamagamesquad, teambgamesquad, venue_id) VALUES (UUID(?), null , false, false,  1, '2023-07-01 14:30:00', 0, UUID(?), null, UUID(?), UUID(?), UUID(?))";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, refereeId);
            preparedStatement.setString(3, gameSquadA);
            preparedStatement.setString(4, gameSquadB);
            preparedStatement.setString(5, createVenue());

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createMzl6EndedGame(String refereeId, String gameSquadA, String gameSquadB) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.games (id, end_time, is_postponed, is_postpone_request, queue, start_time, version, referee_id, score_id, teamagamesquad, teambgamesquad, venue_id) VALUES (UUID(?), '2023-07-07 14:30:00' , false, false,  1, '2023-07-01 14:30:00', 0, UUID(?), null, UUID(?), UUID(?), UUID(?))";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, refereeId);
            preparedStatement.setString(3, gameSquadA);
            preparedStatement.setString(4, gameSquadB);
            preparedStatement.setString(5, createVenue());

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createUnapprovedTeamWithManager(String managerId, String accountId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', false, ?, 0, null, null, null);\n";
            String sqlQuery2 = "UPDATE public.managers SET team_id = UUID(?) WHERE id = UUID(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, randomLogin());
            preparedStatement2.setString(1, uuid);
            preparedStatement2.setString(2, managerId);
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createApprovedTeamWithManager(String managerId, String accountId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        String uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES (UUID(?), 'Warszawa', true, ?, 0, null, null, null);\n";
            String sqlQuery2 = "UPDATE public.managers SET team_id = UUID(?) WHERE id = UUID(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, randomLogin());
            preparedStatement2.setString(1, uuid);
            preparedStatement2.setString(2, managerId);
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            return uuid;
        }
    }



    public static Response registerAccount(String email, String login, String roleType) {
        AccountDTO account = new AccountDTO(UUID.randomUUID(), 0, login, "jacek", "placek", email, null, "pl");
        RoleDTO role = new RoleDTO(roleType);
        RegisterAccountDTO registerAccountDTO = new RegisterAccountDTO(account, "12345678", role);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(registerAccountDTO)
                .log().all()
                .post("/register");
    }

    public Response registerAccount(String email, String login, String roleType, String password) {
        AccountDTO account = new AccountDTO(UUID.randomUUID(), 0, login, "jacek", "placek", email, null, "pl");
        RoleDTO role = new RoleDTO(roleType);
        RegisterAccountDTO registerAccountDTO = new RegisterAccountDTO(account, password, role);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(registerAccountDTO)
                .log().all()
                .post("/register");
    }

    @SneakyThrows
    public void activateAccount(String login) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "UPDATE accounts SET is_active = true WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public void addManagerToTeam(String managerId, String teamId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "UPDATE managers SET team_id = UUID(?) WHERE id = UUID(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setObject(1, teamId);
            preparedStatement.setObject(2, managerId);
            preparedStatement.executeUpdate();
        }
    }

    public static Cookie authCookie(boolean isAdmin) {
        JWTManager jwtManager = new JWTManager();
        List<RoleType> roles = new ArrayList<>();
        CreateTokenDTO createTokenDTO = new CreateTokenDTO(roles, UUID.fromString(adminUUID));
        if (isAdmin) {
            roles.add(RoleType.ADMIN);
            createTokenDTO.setClientId(UUID.fromString(adminUUID));
        } else {
            roles.add(RoleType.MANAGER);
            createTokenDTO.setClientId(UUID.fromString(managerUUID));
        }
        String token = jwtManager.createJWTToken(createTokenDTO);

        return new Cookie.Builder("token", token)
                .setDomain("localhost")
                .setPath("/")
                .build();
    }

    public Cookie authCookie(AccountDTO account) {
        JWTManager jwtManager = new JWTManager();
        List<RoleType> roleTypes = new ArrayList<>();
        for (RoleDTO role : account.getRoles()) {
            Role domainRole = RoleDTOMapper.DTOToRole(role);
            roleTypes.add(domainRole.getRoleType());
        }

        CreateTokenDTO createTokenDTO = new CreateTokenDTO(roleTypes, account.getId());
        String token = jwtManager.createJWTToken(createTokenDTO);

        return new Cookie.Builder("token", token)
                .setDomain("localhost")
                .setPath("/")
                .build();
    }

    @SneakyThrows
    public static boolean isAdminInDb() {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "SELECT * FROM accounts WHERE id = 'd93b7ab9-da9b-490c-a096-5c48437cd3a8';";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);


            ResultSet resultSet = preparedStatement.executeQuery();
            // Retrieve the account details from the result set
            return resultSet.next();
        }
    }

    @SneakyThrows
    public static String createPastGame(String refereeId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();
        createRefGetRoleId();

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.games (id, version, end_time, queue, start_time, referee_id, venue_id) VALUES (UUID(?), '2023-05-05 14:40:48', 0, 1, '2023-05-05 14:00:48', UUID(?), UUID(?));";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, refereeId);
            preparedStatement.setString(3, createVenue());

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createFutureNotEndedGame(String refereeId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();


        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.games (id, version, end_time, queue, start_time, referee_id, venue_id) VALUES (UUID(?), '2023-08-08 14:40:48', 0, 1, '2023-08-08 14:00:48', UUID(?), UUID(?));";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, refereeId);
            preparedStatement.setString(3, createVenue());

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createFutureGame(String refereeId) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();


        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.games (id, version, end_time, queue, start_time, referee_id, venue_id) VALUES (UUID(?), '2023-08-08 14:40:48', 0, 1, '2023-08-08 14:00:48', UUID(?), UUID(?));";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, refereeId);
            preparedStatement.setString(3, createVenue());

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createAcceptedScore(String gameId, int pointsA, int pointsB) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();


        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO scores (id, scoreboard_points_a, scoreboard_points_b, version, approvalteama, approvalteamb) VALUES (UUID(?), (?), (?), 1, 'APPROVED', 'APPROVED');";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, String.valueOf(pointsA));
            preparedStatement.setString(3, String.valueOf(pointsB));

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public static String createUnacceptedScore(int pointsA, int pointsB) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();


        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO scores (id, scoreboard_points_a, scoreboard_points_b, version, approvalTeamA, approvalTeamB) VALUES (UUID(?), (?), (?), 1, 'NONE', 'APPROVED');";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setInt(2, pointsA);
            preparedStatement.setInt(3, pointsB);

            preparedStatement.executeUpdate();
            return uuid;
        }
    }
    @SneakyThrows
    public static String createSet(String scoreId, int pointsA, int pointsB) {
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";
        String uuid = UUID.randomUUID().toString();


        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO sets (id, team_a_points, team_b_points, version, score_id) VALUES (UUID(?), (?), (?), 1, UUID(?));";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setInt(2, pointsA);
            preparedStatement.setInt(3, pointsB);
            preparedStatement.setString(4, scoreId);

            preparedStatement.executeUpdate();
            return uuid;
        }
    }

    @SneakyThrows
    public List<LeagueWithScoreboardsDTO> getScoreboards() {
        Response response = RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(leagueUri+"/scoreboards");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(
                response.getBody().asString(),
                new TypeReference<>() {
                }
        );
    }

    @SneakyThrows
    public ScoreboardDTO getScoreboard(String scoreboardId) {
        Response response = RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get(scoreboardUri+"/"+scoreboardId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(
                response.getBody().asString(),
                new TypeReference<>() {
                }
        );
    }
}