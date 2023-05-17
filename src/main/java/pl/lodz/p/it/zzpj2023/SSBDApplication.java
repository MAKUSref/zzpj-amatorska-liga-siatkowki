package pl.lodz.p.it.zzpj2023;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import pl.lodz.p.it.zzpj2023.roles.Roles;

@ApplicationPath("/api")
@DeclareRoles({Roles.ADMIN, Roles.CAPITAN, Roles.MANAGER, Roles.COACH, Roles.REFEREE})
@ApplicationScoped
public class SSBDApplication extends Application {
}
