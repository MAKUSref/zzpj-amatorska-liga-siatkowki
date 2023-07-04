package pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.GameSquad;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.GameSquadFacade;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class GameSquadManager {
    @Inject
    GameSquadFacade gameSquadFacade;

    public void createGameSqad(GameSquad gs) {
        gameSquadFacade.create(gs);
    }
}
