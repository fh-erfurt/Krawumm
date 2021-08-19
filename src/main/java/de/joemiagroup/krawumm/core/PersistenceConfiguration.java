package de.joemiagroup.krawumm.core;

import org.h2.tools.Server;
import org.springdoc.data.rest.SpringDocDataRestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.sql.SQLException;

/**
 * This class persists the data in the database
 * <br>
 *
 * @author Johannes Otto
 *
 */

@Configuration
@EnableTransactionManagement
@Import(SpringDocDataRestConfiguration.class)
public class PersistenceConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }

}
