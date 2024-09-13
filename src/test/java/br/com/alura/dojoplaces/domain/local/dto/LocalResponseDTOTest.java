package br.com.alura.dojoplaces.domain.local.dto;

import br.com.alura.dojoplaces.domain.local.Local;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LocalResponseDTOTest {

    @Test
    void calculateDaysSince__should_return_correct_number_of_days_since_last_update_or_never_updated() {
        Local local = new Local("Nome", "Código", "Bairro", "Cidade","12230000");
        local.setUpdateDate(LocalDate.now().minusDays(5));
        LocalResponseDTO localResponseDTO = new LocalResponseDTO(local);
        assertEquals("5 dias atrás", localResponseDTO.getDaysSinceLastUpdate());

        local.setUpdateDate(LocalDate.now().minusDays(1));
        localResponseDTO = new LocalResponseDTO(local);
        assertEquals("1 dias atrás", localResponseDTO.getDaysSinceLastUpdate());

        local.setUpdateDate(LocalDate.now());
        localResponseDTO = new LocalResponseDTO(local);
        assertEquals("0 dias atrás", localResponseDTO.getDaysSinceLastUpdate());

        local.setUpdateDate(null);
        localResponseDTO = new LocalResponseDTO(local);
        assertEquals("Nunca atualizado", localResponseDTO.getDaysSinceLastUpdate());
    }

}