package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.domain.local.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.domain.local.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

class LocalCreateValidatorTest {

    @Mock
    private LocalRepository localRepository;

    private LocalCreateValidator localCreateValidator;

    private Errors errors;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        errors = mock(Errors.class);
        localCreateValidator = new LocalCreateValidator(localRepository);
    }

    @Test
    void validate__should_return_no_errors() {
        String uniqueCode = "ABC123";

        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Restaurante", uniqueCode, "Centro", "São Paulo","12230000");

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(false);

        localCreateValidator.validate(localCreateDTO, errors);

        verify(errors, never()).rejectValue("code", "error.localCreateDTO", "Um local com este código já existe");
    }

    @Test
    void validate__should_return_error_when_code_local_already_exists() {
        String duplicateCode = "ABC123";

        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Restaurante", duplicateCode, "Centro", "São Paulo","12230000");

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(true);

        localCreateValidator.validate(localCreateDTO, errors);

        verify(errors).rejectValue("code", "error.localCreateDTO", "Um local com este código já existe");
    }
}
