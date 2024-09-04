package br.com.alura.dojoplaces.domain.local.dto;

import br.com.alura.dojoplaces.domain.local.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

public class LocalResponseDTO {
    private final Long id;
    private final String name;
    private final String code;
    private final String creationDate;
    private final String daysSinceLastUpdate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocalResponseDTO(Local localDTO) {
        this.id = localDTO.getId();
        this.name = localDTO.getName();
        this.code = localDTO.getCode();
        this.creationDate = localDTO.getCreationDate().format(FORMATTER);
        this.daysSinceLastUpdate = calculateDaysSince(Optional.ofNullable(localDTO.getUpdateDate()));
    }

    private String calculateDaysSince(Optional<LocalDate> updateDate) {
        return updateDate
                .map(date -> ChronoUnit.DAYS.between(date, LocalDate.now()) + " dias atrás")
                .orElse("Nunca atualizado");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getDaysSinceLastUpdate() {
        return daysSinceLastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalResponseDTO that = (LocalResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(daysSinceLastUpdate, that.daysSinceLastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, creationDate, daysSinceLastUpdate);
    }
}
