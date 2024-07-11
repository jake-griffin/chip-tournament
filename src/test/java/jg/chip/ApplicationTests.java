package jg.chip;

import jg.chip.controller.AuthenticationController;
import jg.chip.controller.PlayerController;
import jg.chip.controller.TournamentController;
import jg.chip.controller.AccountController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private PlayerController playerController;

    @Autowired
    private TournamentController tournamentController;

    @Autowired
    private AccountController accountController;

    @Test
    void contextLoads() {
        assertNotNull(authenticationController);
        assertNotNull(playerController);
        assertNotNull(tournamentController);
        assertNotNull(accountController);
    }
}
