package com.jedi.isolationlevel.config;

import com.jedi.isolationlevel.constant.DatabaseType;
import com.jedi.isolationlevel.constant.SpringProfile;
import com.jedi.isolationlevel.isolation.truelostupdate.BaseTrueLostUpdate;
import com.jedi.isolationlevel.isolation.truelostupdate.TrueLostUpdateH2;
import com.jedi.isolationlevel.isolation.truelostupdate.TrueLostUpdateMySql;
import com.jedi.isolationlevel.isolation.truelostupdate.TrueLostUpdatePostgreSql;
import com.jedi.isolationlevel.isolation.truelostupdate.TrueLostUpdateSqlServer;
import com.jedi.isolationlevel.isolation.truelostupdate.TrueLostUpdateSqlite;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrueLostUpdateConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.H2)
    public BaseTrueLostUpdate trueLostUpdateH2() {
        return new TrueLostUpdateH2();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.MY_SQL)
    public BaseTrueLostUpdate trueLostUpdateMySql() {
        return new TrueLostUpdateMySql();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.POSTGRE_SQL)
    public BaseTrueLostUpdate trueLostUpdatePostgreSql() {
        return new TrueLostUpdatePostgreSql();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.SQLITE)
    public BaseTrueLostUpdate trueLostUpdateSqlite() {
        return new TrueLostUpdateSqlite();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.SQL_SERVER)
    public BaseTrueLostUpdate trueLostUpdateSqlServer() {
        return new TrueLostUpdateSqlServer();
    }
}
