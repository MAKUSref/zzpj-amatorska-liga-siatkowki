package pl.lodz.p.it.ssbd2023.ssbd04.mzl.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Manager;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.RoleType;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.AddRepresentativeToTeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.CreatePlayerDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.RoleMzlDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TeamDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;

import java.util.UUID;

public class Mzl11AddRepresentativeToTeamTest extends TestUtils {

    private Response addRepresentativeToTeam(AddRepresentativeToTeamDTO addRepresentativeToTeamDTO, AccountDTO accountDTO, boolean isRepresentative, boolean isAdmin, String etagString) {
        if(etagString == null)
        {
            Etag etag = new Etag();
            etagString = etag.calculateSignature(addRepresentativeToTeamDTO);
        }
        if (isRepresentative) {
            if (isAdmin) {
                return RestAssured.given()
                        .spec(requestSpecification)
                        .relaxedHTTPSValidation()
                        .cookie(authCookie(isAdmin))
                        .header("Content-Type", "application/json")
                        .header("If-Match", etagString)
                        .log().all()
                        .body(addRepresentativeToTeamDTO)
                        .post(teamUri + "/addRepresentative");
            } else {
                return RestAssured.given()
                        .spec(requestSpecification)
                        .relaxedHTTPSValidation()
                        .cookie(authCookie(accountDTO))
                        .header("Content-Type", "application/json")
                        .header("If-Match", etagString)
                        .log().all()
                        .body(addRepresentativeToTeamDTO)
                        .post(teamUri + "/addRepresentative");
            }
        } else {
            return RestAssured.given()
                    .spec(requestSpecification)
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .header("If-Match", etagString)
                    .log().all()
                    .body(addRepresentativeToTeamDTO)
                    .post(teamUri + "/addRepresentative");
        }
    }

    @Test
    public void addPlayerToTeamPositiveTestAsAdmin() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        AccountDTO coach1 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        String coach1Id = coach1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        RoleMzlDTO roleMzlDTO = new RoleMzlDTO(RoleType.COACH, coach1.getLogin());
        AddRepresentativeToTeamDTO addRepresentativeToTeamDTO = new AddRepresentativeToTeamDTO(teamDTO.getTeamId(),teamDTO.getTeamVersion(),roleMzlDTO);
        Response response = addRepresentativeToTeam(addRepresentativeToTeamDTO, captain1, true, true, null);
        response.then().statusCode(202);
    }
    @Test
    public void addPlayerToTeamAsRepresentative() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        AccountDTO coach1 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        String coach1Id = coach1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        RoleMzlDTO roleMzlDTO = new RoleMzlDTO(RoleType.COACH, coach1.getLogin());
        AddRepresentativeToTeamDTO addRepresentativeToTeamDTO = new AddRepresentativeToTeamDTO(teamDTO.getTeamId(),teamDTO.getTeamVersion(),roleMzlDTO);
        Response response = addRepresentativeToTeam(addRepresentativeToTeamDTO, captain1, true, false, null);
        response.then().statusCode(202);
    }
    @Test
    public void addPlayerToTeamAsNotRepresentative() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        AccountDTO coach1 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        String coach1Id = coach1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        RoleMzlDTO roleMzlDTO = new RoleMzlDTO(RoleType.COACH, coach1.getLogin());
        AddRepresentativeToTeamDTO addRepresentativeToTeamDTO = new AddRepresentativeToTeamDTO(teamDTO.getTeamId(),teamDTO.getTeamVersion(),roleMzlDTO);
        Response response = addRepresentativeToTeam(addRepresentativeToTeamDTO, captain2, true, false, null);
        response.then().statusCode(409);
    }
    @Test
    public void addPlayerToTeamAsGuest() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        AccountDTO captain2 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        AccountDTO coach1 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        String coach1Id = coach1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        RoleMzlDTO roleMzlDTO = new RoleMzlDTO(RoleType.COACH, coach1.getLogin());
        AddRepresentativeToTeamDTO addRepresentativeToTeamDTO = new AddRepresentativeToTeamDTO(teamDTO.getTeamId(),teamDTO.getTeamVersion(),roleMzlDTO);
        Response response = addRepresentativeToTeam(addRepresentativeToTeamDTO, captain2, false, false, null);
        response.then().statusCode(401);
    }
    @Test
    public void addPlayerToTeamOptimisticLock() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        AccountDTO coach1 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        String coach1Id = coach1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        TeamDTO teamDTOOptimisticLock = new TeamDTO(teamDTO.getTeamId(), teamDTO.getTeamVersion()-1);
        RoleMzlDTO roleMzlDTO = new RoleMzlDTO(RoleType.COACH, coach1.getLogin());
        AddRepresentativeToTeamDTO addRepresentativeToTeamDTO = new AddRepresentativeToTeamDTO(teamDTOOptimisticLock.getTeamId(),teamDTOOptimisticLock.getTeamVersion(),roleMzlDTO);
        Response response = addRepresentativeToTeam(addRepresentativeToTeamDTO, captain1, true, true, null);
        response.then().statusCode(409);
    }

    @Test
    public void addPlayerToTeamETagException() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();

        AccountDTO coach1 = registerAccount(randomEmail(), randomLogin(), "COACH").as(AccountDTO.class);
        String coach1Id = coach1.getRoles().get(0).getId().toString();

        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);
        String etag = getTeamETag(teamDTO.getTeamId().toString());
        TeamDTO teamDTOEtag = new TeamDTO(teamDTO.getTeamId(), teamDTO.getTeamVersion()+10);

        RoleMzlDTO roleMzlDTO = new RoleMzlDTO(RoleType.COACH, coach1.getLogin());

        AddRepresentativeToTeamDTO addRepresentativeToTeamDTO = new AddRepresentativeToTeamDTO(teamDTOEtag.getTeamId(), teamDTOEtag.getTeamVersion(),roleMzlDTO);
        Response response = addRepresentativeToTeam(addRepresentativeToTeamDTO, captain1, true, true, etag);
        response.then().statusCode(412);
    }

    @Test
    public void addPlayerToTeamHasThatRoleException() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);


        RoleMzlDTO roleMzlDTO = new RoleMzlDTO(RoleType.CAPTAIN, captain1.getLogin());

        AddRepresentativeToTeamDTO addRepresentativeToTeamDTO = new AddRepresentativeToTeamDTO(teamDTO.getTeamId(), teamDTO.getTeamVersion(),roleMzlDTO);
        Response response = addRepresentativeToTeam(addRepresentativeToTeamDTO, captain1, true, true, null);
        response.then().statusCode(406);
    }

    @Test
    public void addPlayerToTeamIncorrectRepresentativeExceptionWrongRoleType() {
        AccountDTO captain1 = registerAccount(randomEmail(), randomLogin(), "CAPTAIN").as(AccountDTO.class);
        String captain1Id = captain1.getRoles().get(0).getId().toString();
        TeamDTO teamDTO = createMzl10TeamWithCaptain(captain1Id);


        RoleMzlDTO roleMzlDTO = new RoleMzlDTO(RoleType.COACH, captain1.getLogin());

        AddRepresentativeToTeamDTO addRepresentativeToTeamDTO = new AddRepresentativeToTeamDTO(teamDTO.getTeamId(), teamDTO.getTeamVersion(),roleMzlDTO);
        Response response = addRepresentativeToTeam(addRepresentativeToTeamDTO, captain1, true, true, null);
        response.then().statusCode(409);
    }
}
