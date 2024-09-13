package br.com.alura.dojoplaces.domain.local.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LocalCreateDTOTest {

    private final Validator validator;

    public LocalCreateDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "codigovalido123",
            "CodigoValido123",
            "codigovalido",
            "CodigoValido"
    })
    public void should_accept_code_that_follows_pattern(String code) {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("nome", code, "bairro", "cidade","12230000");

        Set<ConstraintViolation<LocalCreateDTO>> violations = validator.validate(localCreateDTO);

        assertThat(violations.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "codigo_nao_valido123",
            "Codigo Nao Valido123",
            "codigo nao valido",
            "Codigo_Nao_Valido"
    })
    public void should_reject_code_that_follows_pattern(String code) {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("nome", code, "bairro", "cidade","12230000");

        Set<ConstraintViolation<LocalCreateDTO>> violations = validator.validate(localCreateDTO);

        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations
                .stream().anyMatch(violation ->
                        violation.getMessage().equals("O código deve conter apenas letras e números")))
                .isTrue();
    }
}
