package com.jedi.isolationlevel.config;

import com.jedi.isolationlevel.constant.DatabaseType;
import com.jedi.isolationlevel.constant.SpringProfile;
import com.jedi.isolationlevel.isolation.phantomread.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhantomReadConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.H2)
    public BasePhantomRead phantomReadH2() {
        return new PhantomReadH2();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.MY_SQL)
    public BasePhantomRead phantomReadMySql() {
        return new PhantomReadMySql();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.POSTGRE_SQL)
    public BasePhantomRead phantomReadPostgreSql() {
        return new PhantomReadPostgreSql();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.SQLITE)
    public BasePhantomRead phantomReadSqlite() {
        return new PhantomReadSqlite();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.SQL_SERVER)
    public BasePhantomRead phantomReadSqlServer() {
        return new PhantomReadSqlServer();
    }
}
