package pl.lodz.p.it.ssbd2023.ssbd04;

import java.io.*;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.entities.User;
import pl.lodz.p.it.ssbd2023.ssbd04.services.ClientService;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    @Inject
    ClientService clientService;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        clientService.createUser(new User());
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}