package pl.lodz.p.it.ssbd2023.ssbd04;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ejb.AfterCompletion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
import pl.lodz.p.it.ssbd2023.ssbd04.roles.Roles;

@ApplicationPath("/api")
@DeclareRoles({Roles.ADMIN, Roles.CAPTAIN, Roles.MANAGER, Roles.COACH, Roles.REFEREE})
@ApplicationScoped
public class SSBDApplication extends Application {
    public SSBDApplication() {
        super();
        System.setProperty("user.timezone", "GMT");
    }
}
