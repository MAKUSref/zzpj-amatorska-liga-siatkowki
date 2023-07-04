package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "roles", indexes = {
        @Index(name = "account_fk", columnList = "account_id")
})
@NamedQueries( {
    @NamedQuery(name = "Role.findCaptainsByAccountId", query = "SELECT r FROM Role r WHERE r.roleType = 'CAPTAIN' AND r.account.id = :accountId"),
    @NamedQuery(name = "Role.findCoachesByAccountId", query = "SELECT r FROM Role r  WHERE r.roleType = 'COACH' AND r.account.id = :accountId"),
    @NamedQuery(name = "Role.findManagersByAccountId", query = "SELECT r FROM  Role r WHERE r.roleType = 'MANAGER' AND r.account.id = :accountId"),
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role_type")
public class Role extends AbstractEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id")
    @Getter
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", updatable = false, insertable = false)
    @Getter @Setter
    private RoleType roleType;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id", referencedColumnName = "id", updatable = false)
    @Getter @Setter
    private Account account;

    public Role() {
    }
}
