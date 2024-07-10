package jg.chip.adapter;

import jg.chip.model.Account;
import jg.chip.model.dto.AccountResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AccountAdapterTest {

    @InjectMocks
    private AccountAdapter unit;

    private static final long ID = 42L;
    private static final long OTHER_ID = 123L;
    private static final String USERNAME = "mockUsername";
    private static final long PLAYER_ID = 456L;
    private static final boolean ENABLED = true;

    @Test
    void adapt_mine_success() {
        final AccountResponseDto result = unit.adapt(
            Account.builder()
                .id(ID)
                .username(USERNAME)
                .playerId(PLAYER_ID)
                .enabled(ENABLED)
                .build(),
            ID
        );

        assertEquals(new AccountResponseDto(ID, USERNAME, PLAYER_ID, ENABLED, true), result);
    }

    @Test
    void adapt_notMine_success() {
        final AccountResponseDto result = unit.adapt(
            Account.builder()
                .id(OTHER_ID)
                .username(USERNAME)
                .playerId(PLAYER_ID)
                .enabled(ENABLED)
                .build(),
            ID
        );

        assertEquals(new AccountResponseDto(OTHER_ID, USERNAME, PLAYER_ID, ENABLED, false), result);
    }
}
