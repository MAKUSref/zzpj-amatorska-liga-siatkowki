package pl.lodz.p.it.ssbd2023.ssbd04.security.dtos;

public class LoginDataDTO {

    private String login;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}