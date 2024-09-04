package br.com.alura.dojoplaces.domain.local;

import br.com.alura.dojoplaces.domain.local.dto.LocalUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity(name = "Local")
@Table(name = "local")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(unique = true)
    private String code;

    @NotBlank
    @Size(max = 100)
    private String neighborhood;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotNull
    private LocalDate creationDate = LocalDate.now();

    private LocalDate updateDate;

    public Local() {
    }

    public Local(String name, String code, String neighborhood, String city) {
        this.name = name;
        this.code = code;
        this.neighborhood = neighborhood;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public void updateLocal(@Valid LocalUpdateDTO localUpdateDTO) {
        this.name = localUpdateDTO.getName();
        this.code = localUpdateDTO.getCode();
        this.neighborhood = localUpdateDTO.getNeighborhood();
        this.city = localUpdateDTO.getCity();
        this.updateDate = LocalDate.now();
    }
}
