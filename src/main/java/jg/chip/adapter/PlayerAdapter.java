package jg.chip.adapter;

import jg.chip.model.Player;
import jg.chip.model.dto.PlayerResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerAdapter implements Adapter<Player, PlayerResponseDto> {
    @Override
    public PlayerResponseDto adapt(Player player, Long currentPlayerId) {
        final long playerId = player.getId();
        return new PlayerResponseDto(
            playerId,
            player.getName(),
            currentPlayerId != null && playerId == currentPlayerId
        );
    }
}
