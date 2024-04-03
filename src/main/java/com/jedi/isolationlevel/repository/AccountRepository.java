package com.jedi.isolationlevel.repository;

import com.jedi.isolationlevel.isolation.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query(value = "truncate table account", nativeQuery = true)
    void truncate();

    @Modifying
    @Query(value = "VACUUM", nativeQuery = true)
    void vacuum();
}
