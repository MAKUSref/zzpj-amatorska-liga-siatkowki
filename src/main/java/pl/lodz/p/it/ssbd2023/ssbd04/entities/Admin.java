package pl.lodz.p.it.ssbd2023.ssbd04.entities;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT d FROM Admin d"),
    @NamedQuery(name = "Admin.findAllAccountsThatAreAdmin", query = "SELECT d.account FROM Admin d"),
})
@ToString(callSuper = true)
public class Admin extends Role {
    public Admin() {
        this.setRoleType(RoleType.ADMIN);
    }
}
