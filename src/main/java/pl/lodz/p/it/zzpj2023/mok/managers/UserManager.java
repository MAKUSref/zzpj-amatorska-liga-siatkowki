package pl.lodz.p.it.zzpj2023.mok.managers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.it.zzpj2023.mok.repositories.UserRepository;

@ApplicationScoped
public class UserManager {
    @Inject
    UserRepository userRepository;

}
