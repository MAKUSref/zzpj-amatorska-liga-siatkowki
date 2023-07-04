package pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Role;

import java.util.List;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class RoleFacadeMzl extends AbstractFacade<Role> {

    @PersistenceContext(unitName = "siatka_mzl")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public RoleFacadeMzl() {
        super(Role.class);
    }
    @Override
    public void create(Role entity) {
        super.create(entity);
    }

    @Override
    public void edit(Role entity) {
        super.edit(entity);
    }

    @Override
    public void remove(Role entity) {
        super.remove(entity);
    }

    @Override
    protected List<Role> findAll() {
        return super.findAll();
    }

    @Override
    @RolesAllowed("addRepresentativeToTeam")
    public Role find(Object id) {
        return super.find(id);
    }
}
