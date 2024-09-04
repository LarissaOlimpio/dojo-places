package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.domain.local.dto.LocalUpdateDTO;
import br.com.alura.dojoplaces.domain.local.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

class LocalUpdateValidatorTest {

    @Mock
    private LocalRepository localRepository;

    private LocalUpdateValidator localUpdateValidator;

    private Errors errors;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        errors = mock(Errors.class);
        localUpdateValidator = new LocalUpdateValidator(localRepository);
    }

    @Test
    void validate__should_return_false_when_code_is_not_repeated() {
        String code = "123456";
        Long updateId = 1L;

        LocalUpdateDTO localUpdateDTO = new LocalUpdateDTO();
        localUpdateDTO.setCode(code);
        localUpdateDTO.setId(updateId);

        when(localRepository.existsByCodeAndIdNot(code, updateId)).thenReturn(false);

        localUpdateValidator.validate(localUpdateDTO, errors);

        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    void validate__should_return_true_when_code_is_repeated() {
        String code = "123456";
        Long updateId = 1L;

        LocalUpdateDTO localUpdateDTO = new LocalUpdateDTO();
        localUpdateDTO.setCode(code);
        localUpdateDTO.setId(updateId);

        when(localRepository.existsByCodeAndIdNot(code, updateId)).thenReturn(true);

        localUpdateValidator.validate(localUpdateDTO, errors);

        verify(errors).rejectValue("code", "error.localUpdateDTO", "Um local com este código já existe");
    }

}

