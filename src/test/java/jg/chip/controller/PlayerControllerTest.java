package jg.chip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jg.chip.model.dto.CreatePlayerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void createPlayer_admin_success() throws Exception{
        this.mockMvc
            .perform(
                post("/players")
                    .content(asJsonString(new CreatePlayerDto("New player")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("New player"))
            .andExpect(jsonPath("$.mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void createPlayer_blankName_badRequest() throws Exception{
        this.mockMvc
            .perform(
                post("/players")
                    .content(asJsonString(new CreatePlayerDto("  ")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getAllPlayers_admin_success() throws Exception{
        this.mockMvc
            .perform(get("/players"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Player"))
            .andExpect(jsonPath("$[0].mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getPlayerById_admin_success() throws Exception{
        this.mockMvc
            .perform(get("/players/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Player"))
            .andExpect(jsonPath("$.mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getPlayerById_invalidId_notFound() throws Exception{
        this.mockMvc
            .perform(get("/players/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
