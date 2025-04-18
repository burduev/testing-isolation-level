package com.jedi.isolationlevel.config;

import com.jedi.isolationlevel.constant.DatabaseType;
import com.jedi.isolationlevel.constant.SpringProfile;
import com.jedi.isolationlevel.isolation.dirtyread.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirtyReadConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.H2)
    public BaseDirtyRead dirtyReadH2() {
        return new DirtyReadH2();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.MY_SQL)
    public BaseDirtyRead dirtyReadMySql() {
        return new DirtyReadMySql();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.POSTGRE_SQL)
    public BaseDirtyRead dirtyReadPostgreSql() {
        return new DirtyReadPostgreSql();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.SQLITE)
    public BaseDirtyRead dirtyReadSqlite() {
        return new DirtyReadSqlite();
    }

    @Bean
    @ConditionalOnProperty(prefix = SpringProfile.PREFIX, name = SpringProfile.NAME, havingValue = DatabaseType.SQL_SERVER)
    public BaseDirtyRead dirtyReadSqlServer() {
        return new DirtyReadSqlServer();
    }
}
