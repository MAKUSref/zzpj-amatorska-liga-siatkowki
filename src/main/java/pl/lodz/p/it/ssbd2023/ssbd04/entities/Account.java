package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.lodz.p.it.ssbd2023.ssbd04.security.PassordHasher;

import java.util.*;

@Entity
@Table(name = "accounts")
@NamedQueries( {
        @NamedQuery(name = "Account.findAll", query = "SELECT d FROM Account d"),
        @NamedQuery(name = "Account.findByLogin", query = "SELECT d FROM Account d WHERE d.login = :login"),
        @NamedQuery(name = "Account.findByEmail", query = "SELECT d FROM Account d WHERE d.email = :email"),
        @NamedQuery(name = "Account.findByRoleId", query = "SELECT a FROM Account a JOIN a.roles r WHERE r.id = :roleId"),
        })
public class Account extends AbstractEntity {

    @Id
    @Column(name = "id")
    @UuidGenerator
    @GeneratedValue
    @Getter
    private UUID id;

    @Column(name = "login", unique = true, nullable = false)
    @Size(min = 1, max = 64)
    @Getter @Setter
    private String login;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 64)
    @Getter
    private String password;

    @Column(name = "name", nullable = false)
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters")
    @Getter @Setter
    private String name;

    @Column(name = "lastname", nullable = false)
    @Size(min = 1, max = 32, message = "Last name must be between 1 and 32 characters")
    @Getter @Setter
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    @Size(max = 128, message = "Email must be max 128 characters")
    @Pattern(regexp = "[A-Za-z0-9.]+@[A-Za-z0-9.]*", message = "Invalid email format")
    @Getter @Setter
    private String email;

    @Column(name = "is_active", nullable = false)
    @Getter @Setter
    private boolean isActive;

    @Column(name = "is_approved", nullable = false)
    @Getter @Setter
    private boolean isApproved;

    @Column(name = "is_blocked", nullable = false)
    @Getter @Setter
    private boolean isBlocked;

    @Column(name = "login_timestamp")
    @Getter @Setter
    private Date loginTimestamp;

    @Column(name = "locale", nullable = false)
    private String locale;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, mappedBy = "account", fetch = FetchType.EAGER)
    @Getter @Setter
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, mappedBy = "account", fetch = FetchType.EAGER)
    @Getter
    private Collection<Token> token = new ArrayList<>();

    @Column(name = "last_failed_login")
    @Getter @Setter
    private Date lastFailedLogin;

    @Column(name = "failed_attempts")
    @Getter @Setter
    private Integer failed_attempts;

    @Column(name = "last_activation_email_sent")
    @Getter @Setter
    private Date lastActivationEmailSent;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "changedAccount")
    @Getter @Setter
    private Collection<AccountHistory> accountHistory = new ArrayList<>();

    public Locale getLocale() {
        return Locale.forLanguageTag(locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale.toLanguageTag();
    }

    public Account(String login, String password, String name, String lastname, String email, boolean isActive, boolean isApproved, Date loginTimestamp, Locale locale) {
        this.login = login;
        setPassword(password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.isActive = isActive;
        this.isApproved = isApproved;
        this.loginTimestamp = loginTimestamp;
        this.locale = locale.toLanguageTag();
    }

    public Account() {

    }

    public Account(String id) {
        this.id = UUID.fromString(id);
    }

    public void setPassword(String password) {
        this.password = PassordHasher.hashPassword(password);
    }
}
