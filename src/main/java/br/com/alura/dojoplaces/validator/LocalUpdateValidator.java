package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.domain.local.dto.LocalUpdateDTO;
import br.com.alura.dojoplaces.domain.local.repository.LocalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LocalUpdateValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(LocalUpdateValidator.class);
    private final LocalRepository localRepository;

    private static final String ERROR_CODE_EXISTS = "Um local com este código já existe";

    public LocalUpdateValidator(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LocalUpdateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LocalUpdateDTO localUpdateDTO = (LocalUpdateDTO) target;
        logger.info("Validating LocalUpdateDTO: {}", localUpdateDTO);

        if (localRepository.existsByCodeAndIdNot(localUpdateDTO.getCode(), localUpdateDTO.getId())) {
            logger.warn("Validation failed: code {} already exists", localUpdateDTO.getCode());
            errors.rejectValue("code", "error.localUpdateDTO", ERROR_CODE_EXISTS);
        }

    }

}

