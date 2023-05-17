package pl.lodz.p.it.zzpj2023.mok.entities;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT d FROM Admin d"),
    @NamedQuery(name = "Admin.findAllAccountsThatAreAdmin", query = "SELECT d.user FROM Admin d"),
})
@ToString(callSuper = true)
public class Admin extends Role {
    public Admin() {
    }
}
