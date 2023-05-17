package pl.lodz.p.it.ssbd2023.ssbd04.mok.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends Role {
    public Admin() {
    }
}
