package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@NoArgsConstructor
@Getter @Setter
@NamedQueries({
        @NamedQuery(
                name = "Token.findByToken",
                query = "select v from Token v where v.token = :token"
        ),
        @NamedQuery(
                name = "Token.findByAccountIDAndType",
                query = "select v from Token v where v.account = :account and v.tokenType = :tokenType"
        )
})
public class Token extends AbstractEntity {

    private static final int DEFAULT_EXPIRATION_DAYS = 1;

    @Id
    @Column(name = "id")
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private String token;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "account_id")
    @Getter @Setter
    private Account account;

    private LocalDateTime expiryDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false, updatable = false)
    private TokenType tokenType;

    public Token(String token, Account account, TokenType tokenType) {
        this.token = token;
        this.account = account;
        this.expiryDate = calculateExpiryDate();
        this.tokenType = tokenType;
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusDays(DEFAULT_EXPIRATION_DAYS);
    }
}
