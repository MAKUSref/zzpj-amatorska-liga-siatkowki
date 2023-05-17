package pl.lodz.p.it.ssbd2023.ssbd04.mok.managers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.repositories.UserRepository;

@ApplicationScoped
public class UserManager {
    @Inject
    UserRepository userRepository;

}
