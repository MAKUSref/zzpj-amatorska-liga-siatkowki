package pl.lodz.p.it.zzpj2023.mok.endpoints;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.zzpj2023.mok.dtos.*;
import pl.lodz.p.it.zzpj2023.mok.entities.Account;
import pl.lodz.p.it.zzpj2023.mok.entities.Role;
import pl.lodz.p.it.zzpj2023.mok.managers.AccountManager;
import pl.lodz.p.it.zzpj2023.mok.mappers.RoleDTOMapper;
import pl.lodz.p.it.zzpj2023.mok.mappers.UserDTOMapper;
import pl.lodz.p.it.zzpj2023.roles.Roles;
import pl.lodz.p.it.zzpj2023.utils.etag.Etag;
import pl.lodz.p.it.zzpj2023.utils.etag.EtagFilter;

import java.util.List;
import java.util.UUID;

@Path("/account")
@RequestScoped
public class AccountEndpoint {

    @Inject
    private AccountManager accountManager;

    @Inject
    private Etag etag;


    @PATCH
    @Path("/addRoleManager")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(Roles.MANAGER)
    public Response addRoleManager(@NotNull @Valid AddRoleManagerDTO addRoleManagerDTO) {
        accountManager.addAccessLevelManager(addRoleManagerDTO.getUsername(), addRoleManagerDTO.getTeam());
        return Response.ok().build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    public Response registerUser(@NotNull @Valid RegisterUserDTO registerUserDTO) {
        Account newAccount = UserDTOMapper.DTOToUser(registerUserDTO.getUserData(), registerUserDTO.getPassword());
        Role newRoleData = RoleDTOMapper.DTOToRole(registerUserDTO.getRole());
        newAccount.setActive(false);
        newAccount.setBlocked(false);
        newAccount.setApproved(false);
        Account result = accountManager.createManager(newAccount, newRoleData);
        return Response.status(201).entity(UserDTOMapper.UserToDTO(result)).build();
    }

    @POST
    @Path("/createAccountAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(Roles.ADMIN)
    public Response createAccountAsAdmin(@NotNull @Valid UserByAdminDTO userByAdminDTO) {
        Account createdAccount = accountManager.createUserAsAdmin(UserDTOMapper.DTOToUser(userByAdminDTO.getAccountDto(), userByAdminDTO.getPassword()),
                RoleDTOMapper.DTOToRole(userByAdminDTO.getRoleDTO()));
        return Response.ok(UserDTOMapper.UserToDTO(createdAccount)).build();
    }

    @PATCH
    @Path("/editAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed({Roles.MANAGER,Roles.COACH,Roles.CAPITAN,Roles.REFEREE,Roles.ADMIN})
    public Response editUser(@NotNull @Valid EditAccountDTO editAccountDTO) {
        accountManager.editSelfUser(editAccountDTO);

        return Response.ok().build();
    }

    @PATCH
    @Path("/editAccount/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagFilter
    @RolesAllowed({Roles.ADMIN})
    public Response editUserAsAdmin(@PathParam("login") String login, @NotNull @Valid EditAccountDTO editAccountDTO) {
        accountManager.editUserAsAdmin(editAccountDTO, login);

        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(Roles.ADMIN)
    public UserDTO getAccountByUUID(@PathParam("id") UUID id){
        return UserDTOMapper.UserToDTO(accountManager.getUserByUUID(id));
    }

    @PATCH
    @Path("/block/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed(Roles.ADMIN)
    public Response blockAccount(@PathParam("id")UUID id){
        accountManager.blockAccount(id);
        return Response.status(201).build();
    }

    @PATCH
    @Path("/unblock/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed(Roles.ADMIN)
    public Response unblockAccount(@PathParam("id")UUID id){
        accountManager.unblockAccount(id);
        return Response.status(201).build();
    }

    @GET
    @Path("/ping")
    @Produces("plain/text")
    public Response pong(){
        return Response.ok("pong").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllUsers() {
        return UserDTOMapper.UsersToDTOList(accountManager.getAllUsers());
    }

    @GET
    @Path("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllAdmins() {
        return UserDTOMapper.UsersToDTOList(accountManager.getAllAdmins());
    }

    @GET
    @Path("/referee")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllReferees() {
        return UserDTOMapper.UsersToDTOList(accountManager.getAllReferees());
    }

    @GET
    @Path("/coach")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllCoaches() {
        return UserDTOMapper.UsersToDTOList(accountManager.getAllCoaches());
    }

    @GET
    @Path("/captain")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllCaptains() {
        return UserDTOMapper.UsersToDTOList(accountManager.getAllCaptains());
    }

    @GET
    @Path("/manager")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllManagers() {
        return UserDTOMapper.UsersToDTOList(accountManager.getAllManagers());
    }

    @GET
    @Path("/getUserByLogin/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO getUserByLogin(@PathParam("login") String login) {
        return UserDTOMapper.UserToDTO(accountManager.getUserByLogin(login));
    }

    @POST
    @Path("/resetPassword")
    @PermitAll
    public Response resetPassword(@QueryParam("token") String token, @NotNull String newPassword) {
        accountManager.resetPassword(token, newPassword);
        return Response.status(200).build();
    }

    @GET
    @Path("/requestResetPassword")
    @PermitAll
    public Response requestResetPassword(@QueryParam("email") String userEmail) {
        accountManager.sendResetPasswordEmail(userEmail);
        return Response.status(200).build();
    }
}
