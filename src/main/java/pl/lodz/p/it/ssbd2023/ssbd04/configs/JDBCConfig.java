package pl.lodz.p.it.ssbd2023.ssbd04.configs;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.sql.Connection;

@DataSourceDefinition(
        name = "java:app/jdbc/siatka_admin",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "siatka_admin",
        password = "siatka_admin",
        serverName = "localhost",
        portNumber = 5434,
        databaseName = "siatka",
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 1,
        maxIdleTime = 10)
@DataSourceDefinition( // Ta pula połączeń jest na potrzeby implementacji uwierzytelniania w aplikacji
        name = "java:app/jdbc/siatka_mok",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "siatka_mok",
        password = "siatka_mok",
        serverName = "localhost",
        portNumber = 5434,
        databaseName = "siatka"
)
@DataSourceDefinition( // Ta pula połączeń jest na potrzeby implementacji uwierzytelniania w aplikacji
        name = "java:app/jdbc/siatka_mzl",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "siatka_mzl",
        password = "siatka_mzl",
        serverName = "localhost",
        portNumber = 5434,
        databaseName = "siatka"
)
@Stateless
public class JDBCConfig {
    @PersistenceContext(unitName = "siatka_admin")
    private EntityManager em;
}
