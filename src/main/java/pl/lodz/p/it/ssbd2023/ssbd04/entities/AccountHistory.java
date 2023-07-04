package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "accounts_history")
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "AccountHistory.findByLogin", query = "SELECT h FROM AccountHistory h WHERE h.changedAccount.login = :login"),
        @NamedQuery(name = "AccountHistory.findByAccount", query = "SELECT h FROM AccountHistory h WHERE h.changedAccount = :account")
})
public class AccountHistory extends AbstractEntity {

    @Id
    @Column(name = "id")
    @UuidGenerator
    @GeneratedValue
    @Getter
    private UUID id;

    @OneToOne
    @JoinColumn(name = "changed_by")
    @Getter @Setter
    private Account changedBy;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "account_id", nullable = false)
    @Getter @Setter
    private Account changedAccount;

    @Column(name = "changed_at")
    @Getter @Setter
    private Date changedAt;

    @Column(name = "change_type")
    @Getter @Setter
    private String changeType;

    @Column(name = "property")
    @Getter @Setter
    private String property;

    public AccountHistory(Account changedBy, Account changedAccount, Date changedAt, String changeType, String property) {
        this.changedBy = changedBy;
        this.changedAccount = changedAccount;
        this.changedAt = changedAt;
        this.changeType = changeType;
        this.property = property;
    }
}
