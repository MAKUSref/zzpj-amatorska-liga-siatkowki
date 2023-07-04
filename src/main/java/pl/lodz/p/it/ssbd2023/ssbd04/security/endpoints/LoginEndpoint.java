package pl.lodz.p.it.ssbd2023.ssbd04.security.endpoints;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import pl.lodz.p.it.ssbd2023.ssbd04.security.JWTManager;
import pl.lodz.p.it.ssbd2023.ssbd04.security.dtos.LoginDataDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.security.managers.SecurityManager;

import java.util.logging.Logger;

@Path("/auth")
@RequestScoped
public class LoginEndpoint {

    @Inject
    private SecurityManager securityManager;

    @Inject
    HttpServletRequest request;

    private Logger logger = Logger.getLogger(LoginEndpoint.class.getName());

    Config config = ConfigProvider.getConfig();


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response authenticate(@NotNull @Valid LoginDataDTO loginDataDTO) {
        JWTManager jwtManager = new JWTManager();

        String token = securityManager.logInAccount(loginDataDTO);

        Cookie c = new Cookie("token", token, "/", config.getValue("server.domain", String.class));
        NewCookie cookie = new NewCookie(c, "Token cookie", config.getValue("jwt.duration", int.class) * 60, false);
        String ipAddress = request.getRemoteAddr();
        logger.info("Zalogowany adres ip: " + ipAddress);
        return Response.accepted(token).cookie(cookie).build();

    }

    @GET
    @Path("/ping")
    @Produces("plain/text")
    public Response pong(){
        return Response.ok("pong").build();
    }

}
