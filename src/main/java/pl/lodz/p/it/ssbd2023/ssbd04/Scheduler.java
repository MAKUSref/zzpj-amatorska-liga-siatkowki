package pl.lodz.p.it.ssbd2023.ssbd04;

import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.NotFoundAccountException;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.managers.AccountManager;

import java.util.UUID;

@Singleton
public class Scheduler {
    @Inject
    private AccountManager accountManager;

    @Schedule(hour = "*", minute = "*/30", persistent = false)
    public void checkIfActivated(){
        accountManager.checkIfActivated();
    }
}
