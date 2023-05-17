package pl.lodz.p.it.ssbd2023.ssbd04.mok.entities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
public class User extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "USER_SEQ_GEN", sequenceName = "user_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN")
    @Getter
    private UUID id;

    @Column(name = "login", unique = true, nullable = false)
    @Getter @Setter
    private String login;

    @Column(name = "password", nullable = false)
    @Getter
    private String password;

    @Column(name = "name", nullable = false)
    @Getter @Setter
    private String name;

    @Column(name = "lastname", nullable = false)
    @Getter @Setter
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
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
    @Getter @Setter
    private Locale locale;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "user")
    @Getter
    private Collection<Role> roles = new ArrayList<>();

    public User(String login, String password, String name, String lastname, String email, boolean isActive, boolean isApproved, Date loginTimestamp, Locale locale, Collection<Role> roles) {
        this.login = login;
        setPassword(password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.isActive = isActive;
        this.isApproved = isApproved;
        this.loginTimestamp = loginTimestamp;
        this.locale = locale;
        this.roles = roles;
    }

    public User() {

    }

    public void setPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                stringBuffer.append(Integer.toString((encodedhash[i] & 0xff) + 0x100, 16).substring(1));
            }
            this.password = stringBuffer.toString();
        } catch (NoSuchAlgorithmException ex) {
        }
    }
}
