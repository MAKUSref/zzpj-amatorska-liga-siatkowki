package pl.lodz.p.it.zzpj2023.mok.managers;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pl.lodz.p.it.zzpj2023.exceptions.TokenExpiredException;
import pl.lodz.p.it.zzpj2023.exceptions.UserNotApprovedException;
import pl.lodz.p.it.zzpj2023.mok.entities.PasswordResetToken;
import pl.lodz.p.it.zzpj2023.mok.entities.User;
import pl.lodz.p.it.zzpj2023.mok.facades.TokenFacade;

import java.time.LocalDateTime;
import java.util.UUID;

@Stateless
public class TokenManager {
    @Inject
    private TokenFacade tokenFacade;

    private void checkToken(PasswordResetToken token) {
        if (LocalDateTime.now().isAfter(token.getExpiryDate())) {
            throw new TokenExpiredException();
        }
    }

    @PermitAll
    public User findUserByToken(String token) {
        PasswordResetToken resetToken = tokenFacade.findByToken(token);
        checkToken(resetToken);
        tokenFacade.remove(resetToken);
        return resetToken.getUser();
    }

    @PermitAll
    private void validateUser(User user) {
        if (!user.isActive() || !user.isApproved() || user.isBlocked()) {
            throw new UserNotApprovedException();
        }
    }

    @PermitAll
    public String generateResetPasswordToken(User user) {
        validateUser(user);
        PasswordResetToken token = new PasswordResetToken(UUID.randomUUID().toString(), user);
        tokenFacade.create(token);
        return token.getToken();
    }
}
