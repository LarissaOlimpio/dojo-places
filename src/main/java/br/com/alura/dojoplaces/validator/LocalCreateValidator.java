package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.domain.local.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.domain.local.repository.LocalRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class LocalCreateValidator implements Validator {

    private final LocalRepository localRepository;
    private static final String ERROR_CODE_EXISTS = "Um local com este código já existe";

    public LocalCreateValidator(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LocalCreateDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        LocalCreateDTO localCreateDTO = (LocalCreateDTO) target;

        if (localRepository.existsByCode(localCreateDTO.getCode())) {
            errors.rejectValue("code", "error.localUpdateDTO", ERROR_CODE_EXISTS);
        }

    }

}
