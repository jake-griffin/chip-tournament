package jg.chip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jg.chip.model.dto.LoginDto;
import jg.chip.model.dto.RegisterAccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login_correctPassword_success() throws Exception{
        this.mockMvc
            .perform(
                post("/login")
                    .content(asJsonString(new LoginDto("admin", "12345")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountId").value(1))
            .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void login_incorrectPassword_unauthorized() throws Exception{
        this.mockMvc
            .perform(
                post("/login")
                    .content(asJsonString(new LoginDto("admin", "67890")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void login_disabledUser_unauthorized() throws Exception{
        this.mockMvc
            .perform(
                post("/login")
                    .content(asJsonString(new LoginDto("disabledUser", "12345")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void register_newAccount_success() throws Exception{
        this.mockMvc
            .perform(
                post("/register")
                    .content(asJsonString(new RegisterAccountDto("new", "12345")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void register_alreadyExists_badRequest() throws Exception{
        this.mockMvc
            .perform(
                post("/register")
                    .content(asJsonString(new RegisterAccountDto("admin", "67890")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
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
