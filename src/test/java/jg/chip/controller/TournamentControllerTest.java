package jg.chip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jg.chip.model.dto.CreateTournamentDto;
import jg.chip.model.dto.UpdateTournamentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void createTournament_admin_success() throws Exception{
        this.mockMvc
            .perform(
                post("/tournaments")
                    .content(asJsonString(new CreateTournamentDto("New tournament")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("New tournament"))
            .andExpect(jsonPath("$.mine").value(true))
            .andExpect(jsonPath("$.created").exists());
    }

    @Test
    @WithMockUser(username="account", roles="USER")
    public void createTournament_nonAdmin_forbidden() throws Exception{
        this.mockMvc
            .perform(
                post("/tournaments")
                    .content(asJsonString(new CreateTournamentDto("New tournament")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getTournamentsToday_admin_success() throws Exception{
        this.mockMvc
            .perform(get("/tournaments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Today"))
            .andExpect(jsonPath("$[0].mine").value(true))
            .andExpect(jsonPath("$[0].created").exists());
    }

    @Test
    @WithMockUser(username="user", roles="USER")
    public void getTournamentsToday_user_success() throws Exception{
        this.mockMvc
            .perform(get("/tournaments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Today"))
            .andExpect(jsonPath("$[0].mine").value(false))
            .andExpect(jsonPath("$[0].created").exists());
    }

    @Test
    @WithMockUser(username="otherAdmin", roles="ADMIN")
    public void getTournamentArchive_mine_success() throws Exception{
        this.mockMvc
            .perform(get("/tournaments/archive"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(2))
            .andExpect(jsonPath("$[0].name").value("Yesterday"))
            .andExpect(jsonPath("$[0].mine").value(true))
            .andExpect(jsonPath("$[0].created").exists());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getTournamentArchive_notMine_success() throws Exception{
        this.mockMvc
            .perform(get("/tournaments/archive"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(2))
            .andExpect(jsonPath("$[0].name").value("Yesterday"))
            .andExpect(jsonPath("$[0].mine").value(false))
            .andExpect(jsonPath("$[0].created").exists());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getMyTournaments_admin_success() throws Exception{
        this.mockMvc
            .perform(get("/tournaments/mine"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Today"))
            .andExpect(jsonPath("$[0].mine").value(true))
            .andExpect(jsonPath("$[0].created").exists());
    }

    @Test
    @WithMockUser(username="account", roles="USER")
    public void getMyTournaments_user_forbidden() throws Exception{
        this.mockMvc
            .perform(get("/tournaments/mine"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="otherAdmin", roles="ADMIN")
    public void getTournamentById_admin_success() throws Exception{
        this.mockMvc
            .perform(get("/tournaments/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Yesterday"))
            .andExpect(jsonPath("$.mine").value(true))
            .andExpect(jsonPath("$.created").exists());
    }

    @Test
    @WithMockUser(username="user", roles="USER")
    public void getTournamentById_user_success() throws Exception{
        this.mockMvc
            .perform(get("/tournaments/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Yesterday"))
            .andExpect(jsonPath("$.mine").value(false))
            .andExpect(jsonPath("$.created").exists());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void renameTournament_admin_success() throws Exception{
        this.mockMvc
            .perform(
                put("/tournaments/1")
                    .content(asJsonString(new UpdateTournamentDto("Renamed tournament")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Renamed tournament"))
            .andExpect(jsonPath("$.mine").value(true))
            .andExpect(jsonPath("$.created").exists());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void renameTournament_notMine_forbidden() throws Exception{
        this.mockMvc
            .perform(
                put("/tournaments/2")
                    .content(asJsonString(new UpdateTournamentDto("Renamed tournament")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void renameTournament_invalidId_notFound() throws Exception{
        this.mockMvc
            .perform(
                put("/tournaments/999")
                    .content(asJsonString(new UpdateTournamentDto("Renamed tournament")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="account", roles="USER")
    public void renameTournament_user_forbidden() throws Exception{
        this.mockMvc
            .perform(
                put("/tournaments/2")
                    .content(asJsonString(new UpdateTournamentDto("Renamed tournament")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="account", roles="USER")
    public void deleteTournament_user_forbidden() throws Exception{
        this.mockMvc
            .perform(
                delete("/tournaments/2")
            )
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="otherAdmin", roles="ADMIN")
    public void deleteTournament_otherAdmin_forbidden() throws Exception{
        this.mockMvc
            .perform(
                delete("/tournaments/1")
            )
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void deleteTournament_admin_success() throws Exception{
        this.mockMvc
            .perform(
                delete("/tournaments/1")
            )
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void deleteTournament_invalidId_notFound() throws Exception{
        this.mockMvc
            .perform(
                delete("/tournaments/999")
            )
            .andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
