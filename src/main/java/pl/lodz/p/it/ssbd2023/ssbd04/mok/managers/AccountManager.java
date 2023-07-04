package pl.lodz.p.it.ssbd2023.ssbd04.mok.managers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.SessionSynchronization;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.security.enterprise.SecurityContext;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractManager;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.*;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.facades.AccountHistoryFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.facades.RolesFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.security.EmailService;
import pl.lodz.p.it.ssbd2023.ssbd04.security.PassordHasher;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class AccountManager extends AbstractManager implements SessionSynchronization {
    @Inject
    AccountFacade accountFacade;

    @Inject
    private AccountHistoryFacade accountHistoryFacade;

    @Inject
    TokenManager tokenManager;

    @Inject
    RolesFacade rolesFaced;
    @Inject
    private SecurityContext securityContext;

    @Inject
    EmailService emailService;

    private Logger logger = Logger.getLogger(AccountManager.class.getName());


    public Account createManager(Account account, Manager manager) {
        return this.createAccount(account, manager);
    }


    public Account createAccount(Account account, Role role) {
        account.getRoles().add(role);
        role.setAccount(account);
        accountFacade.create(account);
        updateAccountHistory(account, account, "Create", "Created");
        return account;
    }

    public Account createAccountAsAdmin(Account account, Role role) {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        account.getRoles().add(role);
        role.setAccount(account);
        accountFacade.create(account);
        updateAccountHistory(accountFacade.find(UUID.fromString(currentAccount)), account, "Create", "Created");
        return account;
    }

    public void editAccount(EditAccountDTO editAccountDTO, Account accountToEdit) {
        if (editAccountDTO.getName() != null) {
            accountToEdit.setName(editAccountDTO.getName());
        }
        if (editAccountDTO.getLastname() != null) {
            accountToEdit.setLastname(editAccountDTO.getLastname());
        }
        accountFacade.edit(accountToEdit);
    }

    public void editSelfAccount(EditAccountDTO editAccountDTO) {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account accountToEdit = accountFacade.find(UUID.fromString(currentAccount));
        if(editAccountDTO.getVersion() < accountToEdit.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }
        editAccount(editAccountDTO, accountToEdit);
        updateAccountHistory(accountFacade.find(UUID.fromString(currentAccount)), accountToEdit, "Update", "Details");
    }

    public void editAccountAsAdmin(EditAccountDTO editAccountDTO, String login) {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account accountToEdit = accountFacade.findByLogin(login);
        if(editAccountDTO.getVersion() < accountToEdit.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }
        editAccount(editAccountDTO, accountToEdit);
        updateAccountHistory(accountFacade.find(UUID.fromString(currentAccount)), accountToEdit, "Update", "Details");
    }

    @RolesAllowed("ADMIN")
    public void addAccessLevel(String login, Role role, long accountVersion) {
        Account account = accountFacade.findByLogin(login);
        final String adminWhoChangesAccessAccountName = securityContext.getCallerPrincipal().getName();
        final boolean isNotTheSameAccountWhoChange = !adminWhoChangesAccessAccountName.equals(account.getId().toString());

        if (role.getRoleType().equals(RoleType.MANAGER) || role.getRoleType().equals(RoleType.CAPTAIN) || role.getRoleType().equals(RoleType.COACH)) {
            throw new RoleNotAllowedException();
        }

        if (isNotTheSameAccountWhoChange) {
            final boolean isActiveAccount = account.isActive();
            if (isActiveAccount) {
                final boolean ifAccountContainRole = account.getRoles().stream()
                        .anyMatch(findRole -> findRole.getRoleType().equals(role.getRoleType()));
                if (!ifAccountContainRole) {
                    role.setAccount(account);
                    account.getRoles().add(role);
                    accountFacade.edit(account);
                    updateAccountHistory(accountFacade.find(UUID.fromString(adminWhoChangesAccessAccountName)), account, "Update", "Access");
                    emailService.sendAddRoleInformation(account.getEmail(), account.getLocale().getLanguage(), role.getRoleType().toString());
                } else {
                    throw new AccountHasThatRole();
                }
            } else {
                throw new AccountIsNotActive();
            }
        } else {
            throw new CanNotAddSelfRoleException();
        }

    }

    @RolesAllowed("ADMIN")
    public void removeAccessLevel(String login, Role role) {
        final String AdminWhoChangesAccessAccountname = securityContext.getCallerPrincipal().getName();
        Account account = accountFacade.findByLogin(login);

        final boolean isNotTheSameAccountWhoChange = !AdminWhoChangesAccessAccountname.equals(account.getId().toString());
        if (isNotTheSameAccountWhoChange) {
            if (account.isActive()) {
                if (account.getRoles().size() > 1) {
                    Role roleToRemove = account.getRoles().stream()
                            .filter(findRole -> findRole.getRoleType().equals(role.getRoleType()))
                            .findAny()
                            .orElseThrow(() -> new AccountRoleNotFound());
                    account.getRoles().remove(roleToRemove);
                    rolesFaced.remove(roleToRemove);
                    updateAccountHistory(accountFacade.find(UUID.fromString(AdminWhoChangesAccessAccountname)), account, "Update", "Access");
                    emailService.sendRemoveRoleInformation(account.getEmail(), account.getLocale().getLanguage(), roleToRemove.toString());
                } else {
                    throw new AccountMustHaveAnyRole();
                }
            } else {
                throw new AccountIsNotActive();
            }
        } else {
            throw new CanNotAddSelfRoleException();
        }
    }

    public Account getAccountByUUID(UUID id) {
        return accountFacade.find(id);
    }

    public void blockAccount(UUID id) {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account accountToBeBlocked = accountFacade.find(id);
        if (accountToBeBlocked == null) {
            throw new NotFoundAccountException();
        }
        if(currentAccount.equals(id.toString())) {
            throw new SelfBlockingException();
        }
        if (accountToBeBlocked.isBlocked()) {
            throw BaseApplicationException.accountAlreadyBlocked();
        }
        accountToBeBlocked.setBlocked(true);
        accountFacade.edit(accountToBeBlocked);
        updateAccountHistory(accountFacade.find(UUID.fromString(currentAccount)), accountToBeBlocked, "Update", "Block");
        emailService.sendBlockAccountInformation(accountToBeBlocked.getEmail(), accountToBeBlocked.getLocale().getLanguage());
    }

    public void blockSelfAccount() {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account accountToBeBlocked = getAccountByUUID(UUID.fromString(currentAccount));
        if (accountToBeBlocked == null) {
            throw new NotFoundAccountException();
        }
        if (accountToBeBlocked.isBlocked()) {
            throw BaseApplicationException.accountAlreadyBlocked();
        }
        accountToBeBlocked.setBlocked(true);
        accountFacade.edit(accountToBeBlocked);
        emailService.sendBlockAccountInformation(accountToBeBlocked.getEmail(), accountToBeBlocked.getLocale().getLanguage());
    }

    public void unblockAccount(UUID id) {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account accountToBeUnblocked = accountFacade.find(id);
        if (accountToBeUnblocked == null) {
            throw new NotFoundAccountException();
        }
        if(currentAccount.equals(id.toString())) {
            throw new SelfBlockingException();
        }
        if (!accountToBeUnblocked.isBlocked()) {
            throw BaseApplicationException.accountAlreadyUnblocked();
        }
        accountToBeUnblocked.setBlocked(false);
        accountFacade.edit(accountToBeUnblocked);
        updateAccountHistory(accountFacade.find(UUID.fromString(currentAccount)), accountToBeUnblocked, "Update", "Unblock");
        emailService.sendUnblockAccountInformationMail(accountToBeUnblocked.getEmail(), accountToBeUnblocked.getLocale().getLanguage());
    }

    public void approveAccount(UUID id) {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account accountToBeApproved = accountFacade.find(id);
        if (accountToBeApproved == null) {
            throw new NotFoundAccountException();
        }
        accountToBeApproved.setApproved(true);
        accountFacade.edit(accountToBeApproved);
        updateAccountHistory(accountFacade.find(UUID.fromString(currentAccount)), accountToBeApproved, "Update", "Approve");
        emailService.sendApproveAccountInformation(accountToBeApproved.getEmail(), accountToBeApproved.getLocale().getLanguage());
    }

    public void disapproveAccount(UUID id) {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account accountToBeDisapproved = accountFacade.find(id);
        if (accountToBeDisapproved == null) {
            throw new NotFoundAccountException();
        }
        accountToBeDisapproved.setApproved(true);
        updateAccountHistory(accountFacade.find(UUID.fromString(currentAccount)), accountToBeDisapproved, "Update", "Disapprove");
        accountFacade.edit(accountToBeDisapproved);
    }

    public void changeLanguage(EditAccountLanguageDTO editAccountLanguageDTO) {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account accountToChangeLanguage = accountFacade.find(UUID.fromString(currentAccount));
        if (editAccountLanguageDTO.getVersion() < accountToChangeLanguage.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }
        if (currentAccount.equals(editAccountLanguageDTO.getLogin())) {
            throw new NotFoundAccountException();
        }
        if (editAccountLanguageDTO.getLocale().equals("pl") || editAccountLanguageDTO.getLocale().equals("en")) {
            accountToChangeLanguage.setLocale(new Locale(editAccountLanguageDTO.getLocale()));
            accountFacade.edit(accountToChangeLanguage);
            updateAccountHistory(accountFacade.find(UUID.fromString(currentAccount)), accountToChangeLanguage, "Update", "Language");
        } else {
            throw new LanguageNotSupportedException();
        }
    }

    public void checkIfActivated() {
        List<Account> accounts = getAllAccounts();
        for(Account accountToCheck: accounts){
            for (Token token : accountToCheck.getToken()) {
                if (token.getTokenType() == TokenType.ACTIVATION && LocalDateTime.now().isAfter(token.getExpiryDate())){
                    if(checkIsEmailPast24Hours(accountToCheck.getLastActivationEmailSent())) {
                        String tmp = accountToCheck.getEmail();
                        String tmp2 = accountToCheck.getLocale().getLanguage();
                        accountFacade.remove(accountToCheck);
                        updateAccountHistory(accountToCheck, accountToCheck, "Delete", "Deleted");
                        emailService.sendDeletedAccountInformationMail(tmp, tmp2);
                    }
                }
            }
        }
    }

    public boolean checkIsEmailPast24Hours(Date lastActivationEmailSent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date twentyFourHoursAgo = calendar.getTime();
        return !lastActivationEmailSent.after(twentyFourHoursAgo);
    }

    //    @RolesAllowed("getAccountByLogin")
    public Account getAccountByLogin(String login) {
        return accountFacade.findByLogin(login);
    }

    //    @PermitAll
    public Account getAccountByEmail(String email) {
        return accountFacade.findByEmail(email);
    }

    public List<Account> getAllAccounts() {
        return accountFacade.findAll();
    }

    //    @RolesAllowed("getAllAdmins")
    public List<Account> getAllAdmins() {
        return accountFacade.findAllAdmins();
    }

    //    @RolesAllowed("getAllManagers")
    public List<Account> getAllManagers() {
        return accountFacade.findAllManagers();
    }

    //    @RolesAllowed("getAllCoaches")
    public List<Account> getAllCoaches() {
        return accountFacade.findAllCoaches();
    }

    //    @RolesAllowed("getAllReferees")
    public List<Account> getAllReferees() {
        return accountFacade.findAllReferees();
    }

    //    @RolesAllowed("getAllCaptains")
    public List<Account> getAllCaptains() {
        return accountFacade.findAllCaptains();
    }

    public List<AccountHistory> getAccountHistory(UUID id) {
        Account account = accountFacade.find(id);
        return accountHistoryFacade.findByAccount(account);
    }

    public List<AccountHistory> getSelfAccountHistory() {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        Account account = accountFacade.find(UUID.fromString(currentAccount));
        return accountHistoryFacade.findByAccount(account);
    }

    @PermitAll
    public void sendResetPasswordEmail(String accountEmail) {
        Account account = getAccountByEmail(accountEmail);

        if (account == null) {
            throw new NotFoundAccountException();
        }
        String token = tokenManager.generateResetPasswordToken(account);
        emailService.sendResetPasswordEmail(accountEmail, token, account.getLocale().getLanguage());
    }

    @PermitAll
    public void sendActivationEmail(String accountEmail) {
        Account account = getAccountByEmail(accountEmail);

        if (account == null) {
            throw new NotFoundAccountException();
        } else {
            String token = tokenManager.generateActivationToken(account);
            emailService.sendActivationEmail(accountEmail, token, account.getLocale().getLanguage());
        }
    }

    @PermitAll
    public void sendEmailChangeEmail(UUID callerId) {
        Account account = accountFacade.find(callerId);
        if (account == null) {
            throw new NotFoundAccountException();
        }

        String token = tokenManager.generateEmailChangeToken(account);
        emailService.sendEmailChangeEmail(account.getEmail(), token, account.getLocale().getLanguage());
    }

    @PermitAll
    public void changeAccountPassword(UUID id, ChangePasswordDTO changePasswordDTO) {
        Account currentAccount = accountFacade.find(UUID.fromString(securityContext.getCallerPrincipal().getName()));
        Account account = getAccountByUUID(id);

        if (changePasswordDTO.getVersion() < account.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }

        account.setPassword(changePasswordDTO.getNewPassword());
        accountFacade.edit(account);
        updateAccountHistory(currentAccount, account, "Update", "Password");
    }

    @PermitAll
    public void changeOwnPassword(UUID id, ChangeOwnPasswordDTO changeOwnPasswordDTO) {
        Account account = getAccountByUUID(id);
        if (changeOwnPasswordDTO.getVersion() < account.getVersion()) {
            throw BaseApplicationException.createOptimisticLockException();
        }
        if (!PassordHasher.checkPassword(changeOwnPasswordDTO.getOldPassword(), account.getPassword())) {
            throw BaseApplicationException.passwordsDoNotMatch();
        } else {
            account.setPassword(changeOwnPasswordDTO.getNewPassword());
            accountFacade.edit(account);
        }
    }

    @PermitAll
    public void resetPassword(String token, String newPassword) {
        Account account = tokenManager.findAccountByResetToken(token);
        account.setPassword(newPassword);
        account.getToken().remove(tokenManager.getObjByToken(token));
        tokenManager.removeUsedToken(token);
        accountFacade.edit(account);
        updateAccountHistory(account, account, "Update", "Password");
    }

    @PermitAll
    public void activateAccount(String token) {
        Account account = tokenManager.findAccountByActivationToken(token);
        account.setActive(true);
        account.getToken().remove(tokenManager.getObjByToken(token));
        tokenManager.removeUsedToken(token);
        accountFacade.edit(account);
        updateAccountHistory(account, account, "Update", "Activation");
        emailService.sendAccountActivationInformationMail(account.getEmail(), account.getLocale().getLanguage());
    }

    @PermitAll
    public void changeEmail(ChangeEmailDTO changeEmailDTO) {
        Account account = tokenManager.findAccountByChangeEmailToken(changeEmailDTO.getToken());
        account.getToken().remove(tokenManager.getObjByToken(changeEmailDTO.getToken()));
        tokenManager.removeUsedToken(changeEmailDTO.getToken());
        account.setEmail(changeEmailDTO.getNewEmail());
        updateAccountHistory(account, account, "Update", "Email");
        accountFacade.edit(account);
    }

    @PermitAll
    public Account getSelfAccountInfo() {
        String currentAccount = securityContext.getCallerPrincipal().getName();
        return accountFacade.find(UUID.fromString(currentAccount));
    }

    public void updateAccountHistory(Account changedBy, Account changedAccount, String changeType, String property){
        AccountHistory accountHistory = new AccountHistory(changedBy, changedAccount, new Date(), changeType, property);
        accountHistoryFacade.create(accountHistory);
    }

}
