package pl.lodz.p.it.zzpj2023.mok.managers;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import org.mindrot.jbcrypt.BCrypt;
import pl.lodz.p.it.zzpj2023.mok.dtos.EditUserDTO;
import pl.lodz.p.it.zzpj2023.mok.entities.Manager;
import pl.lodz.p.it.zzpj2023.mok.entities.Role;
import pl.lodz.p.it.zzpj2023.mok.entities.RoleType;
import pl.lodz.p.it.zzpj2023.mok.entities.User;
import pl.lodz.p.it.zzpj2023.exceptions.NotFoundAccountException;
import pl.lodz.p.it.zzpj2023.mok.entities.*;
import pl.lodz.p.it.zzpj2023.mok.facades.AccountFacade;
import pl.lodz.p.it.zzpj2023.mzl.entities.Team;
import pl.lodz.p.it.zzpj2023.security.EmailService;

import java.util.UUID;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserManager {
    @Inject
    AccountFacade accountFacade;

    @Inject
    TokenManager tokenManager;

    @Inject
    private SecurityContext securityContext;

    @Inject
    EmailService emailService;

    private Logger logger = Logger.getLogger(UserManager.class.getName());

    public User createManager(User user, Role role) {
        return this.createUser(user, role);
    }

    public User createUser(User user, Role role) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(11)));
            user.getRoles().add(role);
            role.setUser(user);
            accountFacade.create(user);
            return user;
    }

    public User createUserAsAdmin(User user, Role role) {
        user.getRoles().add(role);
        role.setUser(user);
        accountFacade.create(user);
        return user;
    }

    public void editUser(EditUserDTO editUserDTO, User userToEdit) {
        if (editUserDTO.getName() != null) {
            userToEdit.setName(editUserDTO.getName());
        }
        if (editUserDTO.getLastname() != null) {
            userToEdit.setLastname(editUserDTO.getLastname());
        }
        if (editUserDTO.getEmail() != null) {
            userToEdit.setEmail(editUserDTO.getEmail());
        }
        accountFacade.edit(userToEdit);
    }

    public void editSelfUser(EditUserDTO editUserDTO){
        String currentUser = securityContext.getCallerPrincipal().getName();
        User userToEdit = accountFacade.findByLogin(currentUser);
        editUser(editUserDTO, userToEdit);
    }

    public void editUserAsAdmin(EditUserDTO editUserDTO, String login){
        User userToEdit = accountFacade.findByLogin(login);
        editUser(editUserDTO, userToEdit);
    }

    public void addAccessLevelManager(String username, Team team) {
        final String AdminWhoChangeAccessUsername = securityContext.getCallerPrincipal().getName();
        if (!AdminWhoChangeAccessUsername.equals(username)) {
            final User user = accountFacade.findByLogin(username);
            if (user.isActive()) {
                if (user.getRoles().contains(RoleType.MANAGER)) {

                } else {
                    final Manager newManager = new Manager(team);
                    newManager.setUser(user);
                    user.getRoles().add(newManager);
                }
            }
        }
    }

    public User getUserByUUID(UUID id){
        return accountFacade.find(id);
    }

    public void blockAccount(UUID id) {
        User userToBeBlocked = getUserByUUID(id);
        userToBeBlocked.setBlocked(true);
        accountFacade.edit(userToBeBlocked);
    }

    public void unblockAccount(UUID id) {
        User userToBeBlocked = getUserByUUID(id);
        userToBeBlocked.setBlocked(false);
        accountFacade.edit(userToBeBlocked);
    }

    @RolesAllowed("getUserByLogin")
    public User getUserByLogin(String login) {
        return accountFacade.findByLogin(login);
    }

    @PermitAll
    public User getUserByEmail(String email) {
        return accountFacade.findByEmail(email);
    }

    @RolesAllowed("getAllUsers")
    public List<User> getAllUsers() {
        return accountFacade.findAll();
    }

    @RolesAllowed("getAllAdmins")
    public List<User> getAllAdmins() {
        return accountFacade.findAllAdmins();
    }

    @RolesAllowed("getAllManagers")
    public List<User> getAllManagers() {
        return accountFacade.findAllManagers();
    }

    @RolesAllowed("getAllCoaches")
    public List<User> getAllCoaches() {
        return accountFacade.findAllCoaches();
    }

    @RolesAllowed("getAllReferees")
    public List<User> getAllReferees() {
        return accountFacade.findAllReferees();
    }

    @RolesAllowed("getAllCaptains")
    public List<User> getAllCaptains() {
        return accountFacade.findAllCaptains();
    }

    @PermitAll
    public void sendResetPasswordEmail(String userEmail) {
        User user = getUserByEmail(userEmail);

        if (user == null) {
            throw new NotFoundAccountException();
        }

        String token = tokenManager.generateResetPasswordToken(user);
        emailService.sendResetPasswordEmail(userEmail, token);
    }

    @PermitAll
    private void changePassword(User user, String newPassword) {
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(11)));
        accountFacade.edit(user);
    }

    @PermitAll
    public void resetPassword(String token, String newPassword) {
        User user = tokenManager.findUserByToken(token);
        changePassword(user, newPassword);
    }
}
