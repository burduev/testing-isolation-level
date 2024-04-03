package com.jedi.isolationlevel.config;

import com.jedi.isolationlevel.constant.DatabaseType;
import com.jedi.isolationlevel.constant.SpringProfile;
import com.jedi.isolationlevel.isolation.lostupdate.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LostUpdateConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.H2)
    public BaseLostUpdate lostUpdateH2() {
        return new LostUpdateH2();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.MY_SQL)
    public BaseLostUpdate lostUpdateMySql() {
        return new LostUpdateMySql();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.POSTGRE_SQL)
    public BaseLostUpdate lostUpdatePostgreSql() {
        return new LostUpdatePostgreSql();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.SQLITE)
    public BaseLostUpdate lostUpdateSqlite() {
        return new LostUpdateSqlite();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.SQL_SERVER)
    public BaseLostUpdate lostUpdateSqlServer() {
        return new LostUpdateSqlServer();
    }
}
