package jg.chip.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getActiveAccounts_admin_success() throws Exception{
        this.mockMvc
            .perform(get("/accounts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].username").value("admin"))
            .andExpect(jsonPath("$[0].playerId").doesNotExist())
            .andExpect(jsonPath("$[0].enabled").value(true))
            .andExpect(jsonPath("$[0].mine").value(true))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].username").value("otherAdmin"))
            .andExpect(jsonPath("$[1].playerId").doesNotExist())
            .andExpect(jsonPath("$[1].enabled").value(true))
            .andExpect(jsonPath("$[1].mine").value(false))
            .andExpect(jsonPath("$[2].id").value(3))
            .andExpect(jsonPath("$[2].username").value("user"))
            .andExpect(jsonPath("$[2].playerId").value(1))
            .andExpect(jsonPath("$[2].enabled").value(true))
            .andExpect(jsonPath("$[2].mine").value(false))
            .andExpect(jsonPath("$[3].id").value(4))
            .andExpect(jsonPath("$[3].username").value("otherUser"))
            .andExpect(jsonPath("$[3].playerId").doesNotExist())
            .andExpect(jsonPath("$[3].enabled").value(true))
            .andExpect(jsonPath("$[3].mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getAllUsers_admin_success() throws Exception{
        this.mockMvc
            .perform(get("/accounts/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].username").value("admin"))
            .andExpect(jsonPath("$[0].playerId").doesNotExist())
            .andExpect(jsonPath("$[0].enabled").value(true))
            .andExpect(jsonPath("$[0].mine").value(true))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].username").value("otherAdmin"))
            .andExpect(jsonPath("$[1].playerId").doesNotExist())
            .andExpect(jsonPath("$[1].enabled").value(true))
            .andExpect(jsonPath("$[1].mine").value(false))
            .andExpect(jsonPath("$[2].id").value(3))
            .andExpect(jsonPath("$[2].username").value("user"))
            .andExpect(jsonPath("$[2].playerId").value(1))
            .andExpect(jsonPath("$[2].enabled").value(true))
            .andExpect(jsonPath("$[2].mine").value(false))
            .andExpect(jsonPath("$[3].id").value(4))
            .andExpect(jsonPath("$[3].username").value("otherUser"))
            .andExpect(jsonPath("$[3].playerId").doesNotExist())
            .andExpect(jsonPath("$[3].enabled").value(true))
            .andExpect(jsonPath("$[3].mine").value(false))
            .andExpect(jsonPath("$[4].id").value(5))
            .andExpect(jsonPath("$[4].username").value("disabledUser"))
            .andExpect(jsonPath("$[4].playerId").doesNotExist())
            .andExpect(jsonPath("$[4].enabled").value(false))
            .andExpect(jsonPath("$[4].mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void getAccountById_admin_success() throws Exception{
        this.mockMvc
            .perform(get("/accounts/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.username").value("otherAdmin"))
            .andExpect(jsonPath("$.playerId").doesNotExist())
            .andExpect(jsonPath("$.enabled").value(true))
            .andExpect(jsonPath("$.mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void assignPlayerToAccount_admin_success() throws Exception{
        this.mockMvc
            .perform(put("/accounts/3/assignPlayer/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.username").value("user"))
            .andExpect(jsonPath("$.playerId").value(1))
            .andExpect(jsonPath("$.enabled").value(true))
            .andExpect(jsonPath("$.mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void assignPlayerToAccount_invalidUserId_notFound() throws Exception{
        this.mockMvc
            .perform(put("/accounts/999/assignPlayer/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void assignPlayerToAccount_invalidPlayerId_notFound() throws Exception{
        this.mockMvc
            .perform(put("/accounts/3/assignPlayer/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void disableAccount_admin_success() throws Exception{
        this.mockMvc
            .perform(put("/accounts/2/disable"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.username").value("otherAdmin"))
            .andExpect(jsonPath("$.playerId").doesNotExist())
            .andExpect(jsonPath("$.enabled").value(false))
            .andExpect(jsonPath("$.mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void disableAccount_ownAccount_badRequest() throws Exception{
        this.mockMvc
            .perform(put("/accounts/1/disable"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void disableAccount_invalidId_notFound() throws Exception{
        this.mockMvc
            .perform(put("/accounts/999/disable"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void enableAccount_admin_success() throws Exception{
        this.mockMvc
            .perform(put("/accounts/5/enable"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(5))
            .andExpect(jsonPath("$.username").value("disabledUser"))
            .andExpect(jsonPath("$.playerId").doesNotExist())
            .andExpect(jsonPath("$.enabled").value(true))
            .andExpect(jsonPath("$.mine").value(false));
    }

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void enableAccount_invalidId_notFound() throws Exception{
        this.mockMvc
            .perform(put("/accounts/999/enable"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());
    }
}
