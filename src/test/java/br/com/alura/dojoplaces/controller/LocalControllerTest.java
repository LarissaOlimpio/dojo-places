package br.com.alura.dojoplaces.controller;

import br.com.alura.dojoplaces.domain.local.Local;
import br.com.alura.dojoplaces.domain.local.repository.LocalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LocalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocalRepository localRepository;

    @AfterEach
    public void cleanDatabase() {
        localRepository.deleteAll();
    }

    @Test
    public void formRegisterLocation__should_show_register_form_successfully() throws Exception {
        mockMvc.perform(get("/local/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocation"))
                .andExpect(model().attributeExists("localCreateDTO"));
    }

    @Test
    public void createLocal__should_create_local_successfully() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/submit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "ABC123")
                .param("name", "Restaurante Central")
                .param("city", "São Paulo")
                .param("neighborhood", "Centro")
                .param("cep","01001000");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/local/list"));

    }

    @Test
    public void createLocal__should_return_to_get_method_when_validation_fails() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/submit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "")
                .param("name", "Restaurante Central")
                .param("city", "São Paulo")
                .param("neighbourhood", "Centro")
                .param("cep","01001000");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocation"))
                .andExpect(model().attributeHasFieldErrors("localCreateDTO", "code"));

    }

    @Test
    public void createLocal__should_return_error_when_code_exists() throws Exception {

        Local local = new Local("Restaurante Central", "ABC123", "Centro", "São Paulo","01001000");
        localRepository.save(local);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/submit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "ABC123")
                .param("name", "Restaurante Central")
                .param("city", "São Paulo")
                .param("neighborhood", "Centro")
                .param("cep","01001000");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocation"))
                .andExpect(model().attributeHasFieldErrors("localCreateDTO", "code"));
    }

    @Test
    public void listLocal__should_show_list_of_locals_successfully() throws Exception {

        Local local1 = new Local("Padaria Pão Doce", "XYZ789", "Jardins", "São Paulo","01415000");
        Local local2 = new Local("Mercado Central", "LMN456", "Vila Mariana", "São Paulo","04110000");

        localRepository.saveAll(List.of(local1, local2));

        mockMvc.perform(get("/local/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/listLocations"))
                .andExpect(model().attributeExists("localResponseDTOList"));

    }

    @Test
    public void updateLocal__should_edit_local_successfully() throws Exception {
        Local local = new Local("Restaurante Central", "ABC123", "Centro", "São Paulo","01001000");
        localRepository.save(local);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(local.getId()))
                .param("code", "ABC123")
                .param("name", "Restaurante Atualizado")
                .param("city", "São Paulo")
                .param("neighborhood", "Centro")
                .param("cep","01001000");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/local/list"));

    }

    @Test
    public void updateLocal__should_return_to_get_method_when_validation_fails() throws Exception {
        Long localId = 1L;
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(localId))
                .param("code", "")
                .param("name", "Restaurante Central")
                .param("city", "São Paulo")
                .param("neighbourhood", "Centro")
                .param("cep","01001000");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/local/updateFormLocal"))
                .andExpect(model().attributeHasFieldErrors("localUpdateDTO", "code"));

    }

    @Test
    public void updateLocal__should_throw_NotFoundException_when_local_not_found() throws Exception {
        Long nonExistentId = 1L;

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(nonExistentId))
                .param("code", "ABC123")
                .param("name", "Restaurante Atualizado")
                .param("city", "São Paulo")
                .param("neighborhood", "Centro")
                .param("cep","01001000");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteLocal__should_delete_a_local_successfully() throws Exception {
        Local local = new Local("Restaurante Central", "ABC123", "Centro", "São Paulo","01001000");
        localRepository.save(local);

        mockMvc.perform(post("/local/delete/{id}", local.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/local/list"));
    }

    @Test
    public void updateLocal__should_return_error_when_code_exists() throws Exception {
        Local existingLocal = new Local("Restaurante Antigo", "ABC123", "Centro", "São Paulo","01001000");
        localRepository.save(existingLocal);

        Local localToUpdate = new Local("Restaurante Novo", "XYZ789", "Centro", "São Paulo","01001000");
        localRepository.save(localToUpdate);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(localToUpdate.getId()))
                .param("code", "ABC123")
                .param("name", "Restaurante Atualizado")
                .param("city", "São Paulo")
                .param("neighborhood", "Centro")
                .param("cep","01001000");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/local/updateFormLocal"))
                .andExpect(model().attributeHasFieldErrors("localUpdateDTO", "code"));
    }

    @Test
    public void updateLocal__should_allow_same_code_for_same_local() throws Exception {
        Local local = new Local("Restaurante Central", "ABC123", "Centro", "São Paulo","01001000");
        localRepository.save(local);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(local.getId()))
                .param("code", "ABC123")
                .param("name", "Restaurante Atualizado")
                .param("city", "São Paulo")
                .param("neighborhood", "Centro")
                .param("cep","01001000");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/local/list"));
    }

    @Test
    public void deleteLocal__should_throw_NotFoundException_when_local_not_found() throws Exception {
        Long nonExistentId = 1L;

        mockMvc.perform(post("/local/delete/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

}

