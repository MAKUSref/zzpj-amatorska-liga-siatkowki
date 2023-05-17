package pl.lodz.p.it.zzpj2023.mok.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "Roles", indexes = {
        @Index(name = "user_fk", columnList = "user_id")
})
@Inheritance(strategy = InheritanceType.JOINED)
public class Role extends AbstractEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id")
    @Getter
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    @Getter @Setter
    private RoleType roleType;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    @Getter @Setter
    private User user;

    public Role() {
    }
}
