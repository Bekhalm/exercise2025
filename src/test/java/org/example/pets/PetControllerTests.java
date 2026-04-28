package org.example.pets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTests {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    PetRepository repository;

    @Test
    void getPetsIsPublic() throws Exception {
        when(repository.findAll()).thenReturn(List.of());

        mvc.perform(get("/pets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void postPetWithoutAuthIsRejected() throws Exception {
        mvc.perform(post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "name": "Whiskers", "species": "cat",
                      "hungerLevel": 40, "happiness": 80 }
                    """))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "alice", roles = "USER")
    void postPetWithAuthCreatesPet() throws Exception {
        Pet saved = new Pet("Whiskers", "cat", 40, 80);
        when(repository.save(org.mockito.ArgumentMatchers.any(Pet.class))).thenReturn(saved);

        mvc.perform(post("/pets")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "name": "Whiskers", "species": "cat",
                      "hungerLevel": 40, "happiness": 80 }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Whiskers"))
            .andExpect(jsonPath("$.species").value("cat"));
    }
}
