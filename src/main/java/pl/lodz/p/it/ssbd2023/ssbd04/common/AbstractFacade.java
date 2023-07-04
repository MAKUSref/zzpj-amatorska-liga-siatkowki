package pl.lodz.p.it.ssbd2023.ssbd04.common;

import jakarta.persistence.EntityManager;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;


public abstract class AbstractFacade<T> implements Closeable {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }
    protected List<T> findAll() {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    public T find(Object id) {
         return getEntityManager().find(entityClass, id);
    }

    @Override
    public void close() throws IOException {
        getEntityManager().close();
    }
}
