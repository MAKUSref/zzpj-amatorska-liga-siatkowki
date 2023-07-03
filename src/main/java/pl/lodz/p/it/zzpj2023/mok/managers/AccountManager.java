package pl.lodz.p.it.zzpj2023.mok.managers;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import org.mindrot.jbcrypt.BCrypt;
import pl.lodz.p.it.zzpj2023.mok.dtos.EditAccountDTO;
import pl.lodz.p.it.zzpj2023.mok.entities.Manager;
import pl.lodz.p.it.zzpj2023.mok.entities.Role;
import pl.lodz.p.it.zzpj2023.mok.entities.RoleType;
import pl.lodz.p.it.zzpj2023.mok.entities.Account;
import pl.lodz.p.it.zzpj2023.exceptions.NotFoundAccountException;
import pl.lodz.p.it.zzpj2023.mok.facades.AccountFacade;
import pl.lodz.p.it.zzpj2023.mzl.entities.Team;
import pl.lodz.p.it.zzpj2023.security.EmailService;

import java.util.UUID;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountManager {
    @Inject
    AccountFacade accountFacade;

    @Inject
    TokenManager tokenManager;

    @Inject
    private SecurityContext securityContext;

    @Inject
    EmailService emailService;

    private Logger logger = Logger.getLogger(AccountManager.class.getName());

    public Account createManager(Account account, Role role) {
        return this.createUser(account, role);
    }

    public Account createUser(Account account, Role role) {
            account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(11)));
            account.getRoles().add(role);
            role.setAccount(account);
            accountFacade.create(account);
            return account;
    }

    public Account createUserAsAdmin(Account account, Role role) {
        account.getRoles().add(role);
        role.setAccount(account);
        accountFacade.create(account);
        return account;
    }

    public void editUser(EditAccountDTO editAccountDTO, Account accountToEdit) {
        if (editAccountDTO.getName() != null) {
            accountToEdit.setName(editAccountDTO.getName());
        }
        if (editAccountDTO.getLastname() != null) {
            accountToEdit.setLastname(editAccountDTO.getLastname());
        }
        if (editAccountDTO.getEmail() != null) {
            accountToEdit.setEmail(editAccountDTO.getEmail());
        }
        accountFacade.edit(accountToEdit);
    }

    public void editSelfUser(EditAccountDTO editAccountDTO){
        String currentUser = securityContext.getCallerPrincipal().getName();
        Account accountToEdit = accountFacade.findByLogin(currentUser);
        editUser(editAccountDTO, accountToEdit);
    }

    public void editUserAsAdmin(EditAccountDTO editAccountDTO, String login){
        Account accountToEdit = accountFacade.findByLogin(login);
        editUser(editAccountDTO, accountToEdit);
    }

    public void addAccessLevelManager(String username, Team team) {
        final String AdminWhoChangeAccessUsername = securityContext.getCallerPrincipal().getName();
        if (!AdminWhoChangeAccessUsername.equals(username)) {
            final Account account = accountFacade.findByLogin(username);
            if (account.isActive()) {
                if (account.getRoles().contains(RoleType.MANAGER)) {

                } else {
                    final Manager newManager = new Manager(team);
                    newManager.setAccount(account);
                    account.getRoles().add(newManager);
                }
            }
        }
    }

    public Account getUserByUUID(UUID id){
        return accountFacade.find(id);
    }

    public void blockAccount(UUID id) {
        Account accountToBeBlocked = getUserByUUID(id);
        accountToBeBlocked.setBlocked(true);
        accountFacade.edit(accountToBeBlocked);
    }

    public void unblockAccount(UUID id) {
        Account accountToBeBlocked = getUserByUUID(id);
        accountToBeBlocked.setBlocked(false);
        accountFacade.edit(accountToBeBlocked);
    }

    @RolesAllowed("getUserByLogin")
    public Account getUserByLogin(String login) {
        return accountFacade.findByLogin(login);
    }

    @PermitAll
    public Account getUserByEmail(String email) {
        return accountFacade.findByEmail(email);
    }

    @RolesAllowed("getAllUsers")
    public List<Account> getAllUsers() {
        return accountFacade.findAll();
    }

    @RolesAllowed("getAllAdmins")
    public List<Account> getAllAdmins() {
        return accountFacade.findAllAdmins();
    }

    @RolesAllowed("getAllManagers")
    public List<Account> getAllManagers() {
        return accountFacade.findAllManagers();
    }

    @RolesAllowed("getAllCoaches")
    public List<Account> getAllCoaches() {
        return accountFacade.findAllCoaches();
    }

    @RolesAllowed("getAllReferees")
    public List<Account> getAllReferees() {
        return accountFacade.findAllReferees();
    }

    @RolesAllowed("getAllCaptains")
    public List<Account> getAllCaptains() {
        return accountFacade.findAllCaptains();
    }

    @PermitAll
    public void sendResetPasswordEmail(String userEmail) {
        Account account = getUserByEmail(userEmail);

        if (account == null) {
            throw new NotFoundAccountException();
        }

        String token = tokenManager.generateResetPasswordToken(account);
        emailService.sendResetPasswordEmail(userEmail, token);
    }

    @PermitAll
    private void changePassword(Account account, String newPassword) {
        account.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(11)));
        accountFacade.edit(account);
    }

    @PermitAll
    public void resetPassword(String token, String newPassword) {
        Account account = tokenManager.findUserByToken(token);
        changePassword(account, newPassword);
    }
}
