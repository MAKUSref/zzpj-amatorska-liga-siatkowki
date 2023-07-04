package pl.lodz.p.it.ssbd2023.ssbd04.mok.endpoints;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Role;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers.AccountHistoryDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers.RoleDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers.AccountDTOMapper;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.Etag;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.EtagFilter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AccountHistoryDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Path("/account")
@RequestScoped

public class AccountEndpoint extends AbstractEndpoint {

    @Inject
    private AccountManager accountManager;

    @Inject
    private Etag etag;

    private static final Logger LOG = Logger.getLogger(AccountEndpoint.class.getName());

    @GET
    @Path("/ping")
    @Produces("plain/text")
    @PermitAll
    public Response pong(){
        return Response.ok("pong").build();
    }


    @PATCH
    @Path("/addRole")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed("addRole")
    public Response addRole(@HeaderParam("If-Match") @NotEmpty @NotNull String tag, @NotNull @Valid AddRoleDTO addRoleDTO, @Context SecurityContext ctx) {
        if (!etag.verifyTag(addRoleDTO, tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        repeatTransaction(() -> accountManager.addAccessLevel(addRoleDTO.getLogin(), RoleDTOMapper.DTOToRole(addRoleDTO.getRole()), addRoleDTO.getVersion()), accountManager);
        return Response.ok().build();
    }

    @PATCH
    @Path("/removeRole")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed("removeRole")
    public Response removeRole(@HeaderParam("If-Match") @NotEmpty @NotNull String tag, @NotNull @Valid RemoveRoleDTO removeRoleDTO,  @Context SecurityContext ctx) {
        if (!etag.verifyTag(removeRoleDTO, tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        repeatTransaction(() -> accountManager.removeAccessLevel(removeRoleDTO.getLogin(), RoleDTOMapper.DTOToRole(removeRoleDTO.getRole())), accountManager);
        return Response.ok().build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @PermitAll
    public Response registerAccount(@NotNull @Valid RegisterAccountDTO registerAccountDTO) {
        Account newAccount = AccountDTOMapper.DTOToAccount(registerAccountDTO.getAccountData(), registerAccountDTO.getPassword());
        Role newRoleData = RoleDTOMapper.DTOToRole(registerAccountDTO.getRole());
        if (Objects.equals(registerAccountDTO.getRole().getRole(), "REFEREE")){
                return Response.status(400).entity("You can't register as referee").build();
        }
        newAccount.setActive(false);
        newAccount.setBlocked(false);
        newAccount.setApproved(false);
        Account result = repeatTransaction(() -> accountManager.createAccount(newAccount, newRoleData), accountManager);
        accountManager.sendActivationEmail(newAccount.getEmail());
        return Response.status(201).entity(AccountDTOMapper.AccountToDTO(result)).build();
    }

    @GET
    @Path("/activate")
    @PermitAll
    public Response activateAccount(@QueryParam("token") String token) {
        accountManager.activateAccount(token);
        return Response.status(200).build();
    }

    @GET
    @Path("/sendActivationEmail")
    @PermitAll
    public Response sendActivationEmail(@QueryParam("email") String accountEmail) {
        accountManager.sendActivationEmail(accountEmail);
        return Response.status(200).build();
    }

    @POST
    @Path("/createAccountAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("createAccountAdmin")
    public Response createAccountAsAdmin(@NotNull @Valid RegisterAccountDTO accountByAdminDTO) {
        Account createdAccount = repeatTransaction(() -> accountManager.createAccountAsAdmin(AccountDTOMapper.DTOToAccount(accountByAdminDTO.getAccountData(), accountByAdminDTO.getPassword()),
                RoleDTOMapper.DTOToRole(accountByAdminDTO.getRole())), accountManager);
        sendActivationEmail(createdAccount.getEmail());
        return Response.status(201).entity(AccountDTOMapper.AccountToDTO(createdAccount)).build();
    }

    @PATCH
    @Path("/editAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed("editAccount")
    public Response editAccount(@HeaderParam("If-Match") @NotEmpty @NotNull String tag, @NotNull @Valid EditAccountDTO editAccountDTO, @Context SecurityContext ctx) {
        if (!etag.verifyTag(editAccountDTO, tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
       repeatTransaction(() -> accountManager.editSelfAccount(editAccountDTO), accountManager);
        return Response.ok().build();
    }

    @PATCH
    @Path("/changePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed("changeOwnPassword")
    public Response changeOwnPassword(@HeaderParam("If-Match") @NotEmpty @NotNull String tag, @Valid ChangeOwnPasswordDTO password, @Context SecurityContext ctx) {
        if (!etag.verifyTag(password, tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        repeatTransaction(() -> accountManager.changeOwnPassword(UUID.fromString(ctx.getUserPrincipal().getName()), password), accountManager);
        return Response.ok().build();
    }

    @PATCH
    @Path("/editAccount/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed("editAccountAsAdmin")
    public Response editAccountAsAdmin(@HeaderParam("If-Match") @NotEmpty @NotNull String tag, @PathParam("login") String login, @NotNull @Valid EditAccountDTO editAccountDTO) {
        if (!etag.verifyTag(editAccountDTO, tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        repeatTransaction(() -> accountManager.editAccountAsAdmin(editAccountDTO, login), accountManager);
        return Response.ok().build();
    }


    @GET
    @Path("/getAccountByUUID/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getAccounts")
    public Response getAccountByUUID(@PathParam("id") UUID id){
        AccountDTO accountDTO = AccountDTOMapper.AccountToDTO(accountManager.getAccountByUUID(id));
        return Response.ok().entity(accountDTO).header("ETAG", etag.calculateSignature(accountDTO)).build();
    }

    @PATCH
    @Path("/block/{id}")
    @RolesAllowed("blockAccount")
    public Response blockAccount(@PathParam("id")UUID id){
        repeatTransaction(() -> accountManager.blockAccount(id), accountManager);
        return Response.status(204).build();
    }

    @PATCH
    @Path("/block/self")
    @RolesAllowed("blockSelfAccount")
    public Response blockSelfAccount(){
        accountManager.blockSelfAccount();
        return Response.status(204).build();
    }

    @PATCH
    @Path("/approve/{id}")
    @RolesAllowed("approveAccount")
    public Response approveAccount(@PathParam("id")UUID id){
        accountManager.approveAccount(id);
        return Response.status(204).build();
    }


    @PATCH
    @Path("/unblock/{id}")
    @RolesAllowed("unblockAccount")
    public Response unblockAccount(@PathParam("id")UUID id){
        repeatTransaction(() -> accountManager.unblockAccount(id), accountManager);
        return Response.status(204).build();
    }

    @PATCH
    @Path("/disapprove/{id}")
    @RolesAllowed("disapproveAccount")
    public Response disapproveAccount(@PathParam("id")UUID id){
        repeatTransaction(() -> accountManager.disapproveAccount(id), accountManager);
        return Response.status(204).build();
    }

    @PATCH
    @Path("/changeLanguage")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed("changeLanguage")
    public Response changeLanguage(@HeaderParam("If-match")@NotEmpty @NotNull String tag, @NotNull @Valid EditAccountLanguageDTO editAccountLanguageDTO, @Context SecurityContext ctx) {
        if (!etag.verifyTag(editAccountLanguageDTO, tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        repeatTransaction(() -> accountManager.changeLanguage(editAccountLanguageDTO), accountManager);
        return Response.status(204).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"getAccounts"})
    public List<AccountDTO> getAllAccounts() {
        return AccountDTOMapper.AccountsToDTOList(accountManager.getAllAccounts());
    }

    @GET
    @Path("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getAccounts")
    public List<AccountDTO> getAllAdmins() {
        return AccountDTOMapper.AccountsToDTOList(accountManager.getAllAdmins());
    }

    @GET
    @Path("/referee")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getAccounts")
    public List<AccountDTO> getAllReferees() {
        return AccountDTOMapper.AccountsToDTOList(accountManager.getAllReferees());
    }

    @GET
    @Path("/coach")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getAccounts")
    public List<AccountDTO> getAllCoaches() {
        return AccountDTOMapper.AccountsToDTOList(accountManager.getAllCoaches());
    }

    @GET
    @Path("/captain")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getAccounts")
    public List<AccountDTO> getAllCaptains() {
        return AccountDTOMapper.AccountsToDTOList(accountManager.getAllCaptains());
    }

    @GET
    @Path("/manager")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getAccounts")
    public List<AccountDTO> getAllManagers() {
        return AccountDTOMapper.AccountsToDTOList(accountManager.getAllManagers());
    }

    @GET
    @Path("/getAccountHistory/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getAccountHistory")
    public List<AccountHistoryDTO> getAccountHistory(@PathParam("uuid")UUID uuid){
        return AccountHistoryDTOMapper.AccountHistoriesToDTOList(accountManager.getAccountHistory(uuid));
    }

    @GET
    @Path("/getSelfAccountHistory")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getSelfAccountHistory")
    public List<AccountHistoryDTO> getSelfAccountHistory(){
        return AccountHistoryDTOMapper.AccountHistoriesToDTOList(accountManager.getSelfAccountHistory());
    }

    @GET
    @Path("/getAccountByLogin/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getAccounts")
    public Response getAccountByLogin(@PathParam("login") String login) {
        AccountDTO accountDTO = AccountDTOMapper.AccountToDTO(accountManager.getAccountByLogin(login));
        return Response.ok().entity(accountDTO).header("ETAG", etag.calculateSignature(accountDTO)).build();
    }

    @POST
    @Path("/changeAccountPassword/{accountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed("changeAccountPassword")
    public Response changeAccountPassword(@HeaderParam("If-match")@NotEmpty @NotNull String tag,@PathParam("accountId") UUID accountId, @NotNull ChangePasswordDTO password, @Context SecurityContext ctx) {
        if (!etag.verifyTag(password, tag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
            repeatTransaction(() -> accountManager.changeAccountPassword(accountId, password), accountManager);
        return Response.ok().build();
    }

    @POST
    @Path("/resetPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response resetPassword(@Valid @NotNull PasswordResetDTO resetPasswordDTO) {
        repeatTransaction(() -> accountManager.resetPassword(resetPasswordDTO.getToken().toString(), resetPasswordDTO.getNewPassword()), accountManager);
        return Response.status(200).build();
    }

    @GET
    @Path("/requestResetPassword")
    @PermitAll
    public Response requestResetPassword(@QueryParam("email") String accountEmail) {
        accountManager.sendResetPasswordEmail(accountEmail);
        return Response.status(200).build();
    }

    @GET
    @Path("/requestEmailChange")
    @PermitAll
    public Response requestEmailChange(@Context SecurityContext ctx) {
        accountManager.sendEmailChangeEmail(UUID.fromString(ctx.getUserPrincipal().getName()));
        return Response.status(200).build();
    }

    @POST
    @Path("/changeEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response changeEmail(@NotNull @Valid ChangeEmailDTO changeEmailDTO) {
        repeatTransaction(() -> accountManager.changeEmail(changeEmailDTO), accountManager);
        return Response.status(200).build();
    }

    @GET
    @Path("/getSelfAccountInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("getSelfAccountInfo")
    public Response getSelfAccountInfo(){
        Account u = accountManager.getSelfAccountInfo();
        AccountDTO accountDTO = AccountDTOMapper.AccountToDTO(u);
        return Response.ok().entity(accountDTO).header("ETAG", etag.calculateSignature(accountDTO)).build();
    }
}
