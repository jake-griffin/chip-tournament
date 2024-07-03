package jg.chip.repository;

import jg.chip.model.Matchup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchupRepository extends JpaRepository<Matchup, Long> {
}
