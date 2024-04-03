package com.jedi.isolationlevel.repository;

import com.jedi.isolationlevel.isolation.Account2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository2 extends JpaRepository<Account2, Long> {
    @Modifying
    @Query(value = "truncate table account", nativeQuery = true)
    void truncate();

    @Modifying
    @Query(value = "VACUUM", nativeQuery = true)
    void vacuum();
}
