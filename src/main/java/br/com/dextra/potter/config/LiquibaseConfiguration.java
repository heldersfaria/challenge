package br.com.dextra.potter.config;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Statement;
import java.sql.Timestamp;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

@Configuration
public class LiquibaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(LiquibaseConfiguration.class);

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {

        removerLocksAntigos(dataSource);

        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setShouldRun(liquibaseProperties.isEnabled());

        log.debug("Configuring Liquibase");

        return liquibase;
    }

    private void removerLocksAntigos(DataSource dataSource) {

        final Timestamp ultimoLock = new Timestamp(currentTimeMillis() - (15 * 60 * 1000));

        final String query = format(
                "update DATABASECHANGELOGLOCK set LOCKED = 0, LOCKGRANTED = null, LOCKEDBY = null WHERE LOCKED = 1 AND LOCKGRANTED<'%s'; commit;", ultimoLock.toString());

        try (Statement stmt = dataSource.getConnection().createStatement()) {

            int updateCount = stmt.executeUpdate(query);

            if (updateCount > 0) {
                log.info("LiquibaseConfiguration.removerLocksAntigos(); Quantidade removida: {}.", updateCount);
            }

        } catch (Exception e) {
            log.warn("Erro ao remover locks. {}", e.getMessage());
        }
    }
}
