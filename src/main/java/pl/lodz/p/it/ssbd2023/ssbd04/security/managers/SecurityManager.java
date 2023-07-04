package pl.lodz.p.it.ssbd2023.ssbd04.security.managers;

import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.account.*;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd04.security.JWTManager;
import pl.lodz.p.it.ssbd2023.ssbd04.security.PassordHasher;
import pl.lodz.p.it.ssbd2023.ssbd04.security.dtos.CreateTokenDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.security.dtos.LoginDataDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.security.facades.AccountFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.security.mappers.AccountMapper;

import java.util.Date;
import java.util.UUID;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class SecurityManager {

    @Inject
    private AccountFacade accountFacade;

    JWTManager jwtManager = new JWTManager();


    public String logInAccount(LoginDataDTO loginDataDTO){

        Account account = accountFacade.findByLogin(loginDataDTO.getLogin());

        if(!account.isActive()){
            throw AccountNotActiveException.accountNotActive();
        } else if (!account.isApproved()) {
            throw AccountNotApprovedException.accountNotapproved();
        } else if (account.isBlocked()) {
            throw AccountBlockedException.accountBlocked();
        }

        if(account.getLastFailedLogin() != null){
            if(account.getFailed_attempts()==3 && account.getLastFailedLogin().after(new Date(new Date().getTime() - 3*60*1000))){
                throw TooManyFailedLoginAttemptsException.tooManyFailedLoginAttempts();
            } else if ( account.getLastFailedLogin().before(new Date(new Date().getTime() - 3*60*1000))) {
                account.setFailed_attempts(0);
            }
        }

        boolean isValid = PassordHasher.checkPassword(loginDataDTO.getPassword(), account.getPassword());
        if(isValid){

            account.setFailed_attempts(0);
            account.setLoginTimestamp(new Date());
            accountFacade.edit(account);
            CreateTokenDTO createTokenDTO = AccountMapper.AccountToCreateTokenDTO(account);
            return jwtManager.createJWTToken(createTokenDTO);
        }else{
            if(account.getFailed_attempts() == null){
                account.setFailed_attempts(1);
            }else{
                account.setFailed_attempts(account.getFailed_attempts()+1);
            }

            account.setLastFailedLogin(new Date());
            accountFacade.edit(account);
            throw BadLoginOrPasswordException.badLoginOrPassword();
        }
    }

    public Account getAccountById(UUID id){
        return accountFacade.find(id);
    }

}
