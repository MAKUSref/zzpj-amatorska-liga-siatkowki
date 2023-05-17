package pl.lodz.p.it.zzpj2023.mok;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import jakarta.inject.Inject;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.endpoints.AccountEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.facades.AccountFacade;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@RunWith(Arquillian.class)
public class CreateUserTest {
//
//    @Test
//    public void emptyTest(){
//        Assertions.assertTrue(true);
//    }
//
//    @Deployment
//    public static Archive<?> createDeployment() {
//        return ShrinkWrap.create(WebArchive.class, "test.war")
//                .addPackage(User.class.getPackage())
//                .addPackage(UserManager.class.getPackage())
//                .addPackage(AccountFacade.class.getPackage())
//                .addPackage(AccountEndpoint.class.getPackage())
//                .addAsResource( "META-INF/persistence.xml");
//    }
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Inject
//    UserTransaction utx;
//
//    @Before
//    public void preparePersistenceTest() throws Exception {
//        clearData();
//        insertData();
//        startTransaction();
//    }
//
//    private void clearData() throws Exception {
//        utx.begin();
//        em.joinTransaction();
//        System.out.println("Dumping old records...");
//        em.createQuery("delete from Game").executeUpdate();
//        utx.commit();
//    }
//
//    private void insertData() throws Exception {
//        utx.begin();
//        em.joinTransaction();
//        System.out.println("Inserting records...");
//
//        new User();
//
//        utx.commit();
//        // clear the persistence context (first-level cache)
//        em.clear();
//    }
//
//    private void startTransaction() throws Exception {
//        utx.begin();
//        em.joinTransaction();
//    }
//
//    @After
//    public void commitTransaction() throws Exception {
//        utx.commit();
//    }

    @Test
    public void createManagerTest(){
        Assertions.assertTrue(true);
    }

}
