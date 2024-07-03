package jg.chip.repository;

import jg.chip.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @Query(value = "SELECT * FROM tournament WHERE created >= CURRENT_DATE ORDER BY created DESC", nativeQuery = true)
    List<Tournament> findAllToday();

    @Query(value = "SELECT * FROM tournament WHERE created < CURRENT_DATE ORDER BY created DESC", nativeQuery = true)
    List<Tournament> findBeforeToday();

    @Query(value = "SELECT * FROM tournament WHERE account_id = ?1 ORDER BY created DESC", nativeQuery = true)
    List<Tournament> findByAccountId(Long accountId);
}
