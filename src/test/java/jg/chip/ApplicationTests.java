package jg.chip;

import jg.chip.controller.AuthenticationController;
import jg.chip.controller.TournamentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private TournamentController tournamentController;

    @Test
    void contextLoads() {
        assertNotNull(authenticationController);
        assertNotNull(tournamentController);
    }
}
