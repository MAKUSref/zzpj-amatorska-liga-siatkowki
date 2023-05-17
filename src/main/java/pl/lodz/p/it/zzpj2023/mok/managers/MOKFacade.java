package pl.lodz.p.it.zzpj2023.mok.managers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import pl.lodz.p.it.zzpj2023.mok.entities.User;

public class MOKFacade extends AbstractFacade<User> {

    private EntityManager em;


    public MOKFacade() {
        super(User.class);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ssbd_pu");
        em = emf.createEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

//     public List<User> findByLoginFilter(String loginFilter) {
//         TypedQuery<User> tq = em.createNamedQuery("User.findByLoginFilter", User.class);
//         tq.setParameter("loginFilter", loginFilter);
//         return tq.getResultList();
//        ArrayList<User> usersCointainsLoginFilter = new ArrayList<>();
//        for (User userFromDB : userRepository) {
//            if(userFromDB.getLogin().contains(loginFilter)) {
//                usersCointainsLoginFilter.add(userFromDB);
//            }
//        }
//        return usersCointainsLoginFilter;
 //   }

//    public boolean isEmptyLogin(String exactLogin) {
//         for (User userFromDB : userRepository) {
//             if(userFromDB.getLogin().equals(exactLogin)) {
//                 return false;
//             }
//             return true;
//         }
//         return false;
//    }
//
//    public List<User> findByExactLogin(String exactLogin) {
//        ArrayList<User> userExactLogin = new ArrayList<>();
//        for (User userFromDB : userRepository) {
//             if(userFromDB.getLogin().equals(exactLogin)) {
//                 userExactLogin.add(userFromDB);
//             }
//         }
//         return userExactLogin;
//    }


}
