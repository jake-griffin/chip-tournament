package jg.chip.repository;

import jg.chip.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM account WHERE LOWER(TRIM(username)) = LOWER(TRIM(?1))", nativeQuery = true)
    Account findByUsername(String username);

    @Query(value = "SELECT * FROM account WHERE enabled", nativeQuery = true)
    List<Account> findAllEnabled();
}
