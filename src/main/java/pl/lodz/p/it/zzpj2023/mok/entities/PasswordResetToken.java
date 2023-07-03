package pl.lodz.p.it.zzpj2023.mok.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_tokens")
@NoArgsConstructor
@Getter @Setter
@NamedQueries({
        @NamedQuery(
                name = "PasswordResetToken.findByToken",
                query = "select v from PasswordResetToken v where v.token = :token"
        ),
})
public class PasswordResetToken extends AbstractEntity {

    private static final int EXPIRATION_DAYS = 1;

    @Id
    @Column(name = "id")
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private String token;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Account account;

    private LocalDateTime expiryDate;

    public PasswordResetToken(String token, Account account) {
        this.token = token;
        this.account = account;
        this.expiryDate = calculateExpiryDate();
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusDays(EXPIRATION_DAYS);
    }
}
