package pl.lodz.p.it.ssbd2023.ssbd04;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestJDBC {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ssbd_pu");
        EntityManager entityManager = emf.createEntityManager();
    }
}
