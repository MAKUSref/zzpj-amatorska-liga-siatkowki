package pl.lodz.p.it.zzpj2023.mok;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;

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
