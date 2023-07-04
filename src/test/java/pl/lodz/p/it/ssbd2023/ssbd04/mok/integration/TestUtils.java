package pl.lodz.p.it.ssbd2023.ssbd04.mok.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RegisterAccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Role;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.RoleType;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers.RoleDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.security.JWTManager;
import pl.lodz.p.it.ssbd2023.ssbd04.security.dtos.CreateTokenDTO;

import java.sql.*;
import java.util.*;

public class TestUtils {

    public static String baseUri;
    public static RequestSpecification requestSpecification;
    public static String adminUUID = "d93b7ab9-da9b-490c-a096-5c48437cd3a8";
    public static String managerUUID = "9d4245e4-d90e-43f2-a9b3-9b5c5e68f8a7";

    @BeforeAll
    static void init() {
        System.setProperty("jwt.duration", "30");
        baseUri = "https://localhost:8181/api/account";
        requestSpecification = new RequestSpecBuilder().setBaseUri(baseUri).build();
        if (!isAdminInDb()) {
            createAdmin();
            createManager();
        }

        RestAssured.defaultParser = Parser.JSON;
    }

    public static AccountDTO getAccount(String id){
        Response response = RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie(authCookie(true))
                .log().all()
                .get("/getAccountByUUID/" + id);
        if (response.getStatusCode() == 200){
            return response.getBody().as(AccountDTO.class);
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

    public static String randomEmail(){
        Random random = new Random();
        return "donotsend" + generateRandomString(random.nextInt(10) + 5)+"@"+generateRandomString(random.nextInt(5) + 3) + "." + generateRandomString(random.nextInt(1) + 3);
    }

    public static String randomLogin(){
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

    @SneakyThrows
    public static void createAdmin(){
        String url = "jdbc:postgresql://localhost:5434/siatka";
        String accountname = "siatka_admin";
        String password = "siatka_admin";

        try (Connection connection = DriverManager.getConnection(url, accountname, password)) {
            String sqlQuery = "INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('d93b7ab9-da9b-490c-a096-5c48437cd3a8', 0, 'TESTadmin@mail.com', true, true, false, 'TESTadmin', 'en', 'TESTadmin', '2023-05-18 18:53:32.000000', 'TESTadmin', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G');\n" +
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
            String sqlQuery = "INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9d4245e4-d90e-43f2-a9b3-9b5c5e68f8a7', 0, 'TESTmanager@mail.com', true, true, false, 'TESTmanager', 'en', 'TESTmanager', '2023-05-18 18:53:32.000000', 'TESTmanager', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G');\n" +
                    "INSERT INTO public.roles (id, version, role_type, account_id) VALUES ('29811b1e-5021-4a3e-9568-c6d76c80764f', 0, 'MANAGER', '9d4245e4-d90e-43f2-a9b3-9b5c5e68f8a7');\n" +
                    "INSERT INTO public.admins (id) VALUES ('29811b1e-5021-4a3e-9568-c6d76c80764f');";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.executeUpdate();
        }
    }

    public static Response registerAccount(String email, String login, String roleType){
        AccountDTO account = new AccountDTO(UUID.randomUUID(),0,login, "jacek","placek", email,null,"pl");
        RoleDTO role = new RoleDTO(roleType);
        RegisterAccountDTO registerAccountDTO = new RegisterAccountDTO(account,"12345678",role);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(registerAccountDTO)
                .log().all()
                .post("/register");
    }

    public Response registerAccount(String email, String login, String roleType, String password){
        AccountDTO account = new AccountDTO(UUID.randomUUID(),0,login, "jacek","placek", email,null,"pl");
        RoleDTO role = new RoleDTO(roleType);
        RegisterAccountDTO registerAccountDTO = new RegisterAccountDTO(account,password,role);

        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(registerAccountDTO)
                .log().all()
                .post("/register");
    }

    @SneakyThrows
    public void activateAccount(String login)  {
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
        for(RoleDTO role : account.getRoles()) {
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
}
