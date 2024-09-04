package br.com.alura.dojoplaces.controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import br.com.alura.dojoplaces.domain.local.Local;
import br.com.alura.dojoplaces.domain.local.dto.LocalResponseDTO;
import br.com.alura.dojoplaces.domain.local.dto.LocalUpdateDTO;
import br.com.alura.dojoplaces.domain.local.repository.LocalRepository;
import br.com.alura.dojoplaces.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LocalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalController localController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(localController).build();
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
        when(localRepository.existsByCode("123")).thenReturn(false);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/submit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "123")
                .param("name", "Name")
                .param("city", "City")
                .param("neighborhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/local/list"));

        verify(localRepository, times(1)).save(any(Local.class));
    }

    @Test
    public void createLocal__should_return_to_get_method_when_validation_fails() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/submit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "")
                .param("name", "Name")
                .param("city", "City")
                .param("neighbourhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocation"))
                .andExpect(model().attributeHasFieldErrors("localCreateDTO", "code"));

        verify(localRepository, never()).save(any(Local.class));
    }

    @Test
    public void createLocal__should_return_error_when_code_exists() throws Exception {
        when(localRepository.existsByCode("123")).thenReturn(true);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/submit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "123")
                .param("name", "Name")
                .param("city", "City")
                .param("neighbourhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocation"))
                .andExpect(model().attributeHasFieldErrors("localCreateDTO", "code"))
                .andExpect(model().attributeHasFieldErrorCode("localCreateDTO", "code", "error.localCreateDTO"));

        verify(localRepository, never()).save(any(Local.class));
    }

    @Test
    public void listLocal__should_show_list_of_locals_successfully() throws Exception {
        Local local1 = new Local();
        Local local2 = new Local();

        List<Local> locals = List.of(local1, local2);

        when(localRepository.findAll()).thenReturn(locals);

        LocalResponseDTO dto1 = new LocalResponseDTO(local1);
        LocalResponseDTO dto2 = new LocalResponseDTO(local2);

        List<LocalResponseDTO> localsDtos = List.of(dto1, dto2);

        mockMvc.perform(get("/local/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/listLocations"))
                .andExpect(model().attribute("localResponseDTOList", localsDtos));

        verify(localRepository).findAll();
    }

    @Test
    public void updateLocal__should_edit_local_successfully() throws Exception {
        Long localId = 1L;
        Local local = new Local();
        when(localRepository.findById(localId)).thenReturn(Optional.of(local));

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(localId))
                .param("code", "123")
                .param("name", "Name")
                .param("city", "City")
                .param("neighborhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/local/list"));

        verify(localRepository).save(any(Local.class));
    }

    @Test
    public void updateLocal__should_return_to_get_method_when_validation_fails() throws Exception {
        Long localId = 1L;
        Local local = new Local();
        when(localRepository.findById(localId)).thenReturn(Optional.of(local));

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/local/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(localId))
                .param("code", "")
                .param("name", "Name")
                .param("city", "City")
                .param("neighbourhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/local/updateFormLocal"))
                .andExpect(model().attributeHasFieldErrors("localUpdateDTO", "code"));

        verify(localRepository, never()).save(any(Local.class));
    }

    @Test
    public void updateLocal__should_throw_NotFoundException_when_local_not_found() throws Exception {
        LocalUpdateDTO localUpdateDTO = new LocalUpdateDTO();
        localUpdateDTO.setId(1L);

        BindingResult bindingResult = new BeanPropertyBindingResult(localUpdateDTO, "localUpdateDTO");
        Model model = new ConcurrentModel();

        when(localRepository.findById(localUpdateDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            localController.updateLocal(localUpdateDTO, bindingResult,model );
        });
    }

    @Test
    public void deleteLocal__should_delete_a_local_successfully() throws Exception {
        Long localId = 1L;
        Local local = new Local();
        when(localRepository.existsById(localId)).thenReturn(true);
        when(localRepository.findById(localId)).thenReturn(Optional.of(local));

        mockMvc.perform(post("/local/delete/{id}", localId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/local/list"));

        verify(localRepository).deleteById(localId);
    }

    @Test
    public void deleteLocal__should_throw_NotFoundException_when_local_not_found() throws Exception {
        Long localId = 1L;

        when(localRepository.existsById(localId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            localController.deleteLocal(localId);
        });
    }

}

