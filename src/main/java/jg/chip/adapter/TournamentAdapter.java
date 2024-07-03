package jg.chip.adapter;

import jg.chip.model.Tournament;
import jg.chip.model.dto.TournamentResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TournamentAdapter implements Adapter<Tournament, TournamentResponseDto> {
    @Override
    public TournamentResponseDto adapt(Tournament tournament, Long currentAccountId) {
        return new TournamentResponseDto(
            tournament.getId(),
            tournament.getName(),
            tournament.getCreatedById().equals(currentAccountId),
            tournament.getCreated()
        );
    }
}
