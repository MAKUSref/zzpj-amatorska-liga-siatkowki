package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Coach;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericFacadeExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Captain;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({GenericFacadeExceptionsInterceptor.class, TrackerInterceptor.class})
public class CaptainFacadeMzl extends AbstractFacade<Captain> {
    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;
    public CaptainFacadeMzl() {
        super(Captain.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Captain entity) {
        super.create(entity);
    }

    @Override
    @RolesAllowed("addRepresentativeToTeam")
    public void edit(Captain entity) {
        super.edit(entity);
    }

    @Override
    public void remove(Captain entity) {
        super.remove(entity);
    }

    @Override
    protected List<Captain> findAll() {
        return super.findAll();
    }

    @Override
    @RolesAllowed("addRepresentativeToTeam")
    public Captain find(Object id) {
        return super.find(id);
    }

}
