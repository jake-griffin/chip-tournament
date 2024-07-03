package jg.chip.adapter;

import jg.chip.model.Tournament;
import jg.chip.model.dto.TournamentResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TournamentAdapterTest {

    @InjectMocks
    private TournamentAdapter unit;

    private static final long ID = 42L;
    private static final String NAME = "mockName";
    private static final long ACCOUNT_ID = 123L;
    private static final long OTHER_ACCOUNT_ID = 456L;
    private static final Timestamp CREATED = new Timestamp(new Date().getTime());

    @Test
    void adapt_mine_success() {
        final TournamentResponseDto result = unit.adapt(
            Tournament.builder()
                .id(ID)
                .name(NAME)
                .createdById(ACCOUNT_ID)
                .created(CREATED)
                .build(),
            ACCOUNT_ID
        );

        assertEquals(new TournamentResponseDto(ID, NAME, true, CREATED), result);
    }

    @Test
    void adapt_notMine_success() {
        final TournamentResponseDto result = unit.adapt(
            Tournament.builder()
                .id(ID)
                .name(NAME)
                .createdById(OTHER_ACCOUNT_ID)
                .created(CREATED)
                .build(),
            ACCOUNT_ID
        );

        assertEquals(new TournamentResponseDto(ID, NAME, false, CREATED), result);
    }
}
