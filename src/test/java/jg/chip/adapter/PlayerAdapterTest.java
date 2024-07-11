package jg.chip.adapter;

import jg.chip.model.Player;
import jg.chip.model.dto.PlayerResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PlayerAdapterTest {

    @InjectMocks
    private PlayerAdapter unit;

    private static final long ID = 42L;
    private static final long OTHER_ID = 123L;
    private static final String NAME = "mockName";

    @Test
    void adapt_mine_success() {
        final PlayerResponseDto result = unit.adapt(
            Player.builder()
                .id(ID)
                .name(NAME)
                .build(),
            ID
        );

        assertEquals(new PlayerResponseDto(ID, NAME, true), result);
    }

    @Test
    void adapt_notMine_success() {
        final PlayerResponseDto result = unit.adapt(
            Player.builder()
                .id(OTHER_ID)
                .name(NAME)
                .build(),
            ID
        );

        assertEquals(new PlayerResponseDto(OTHER_ID, NAME, false), result);
    }
}
