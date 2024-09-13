package br.com.alura.dojoplaces.domain.local.dto;

import br.com.alura.dojoplaces.domain.local.Local;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LocalCreateDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "O código não pode estar em branco")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "O código deve conter apenas letras e números")
    @Column(unique = true)
    private String code;

    @NotBlank(message = "O bairro não pode estar em branco")
    @Size(max = 100)
    private String neighborhood;

    @NotBlank(message = "A cidade não pode estar em branco")
    @Size(max = 100)
    private String city;

    @NotBlank(message = "O Cep não pode estar em branco")
    @Pattern(regexp = "^[0-9]{8}$", message = "O cep deve conter apenas números")
    private String cep;

    public LocalCreateDTO(String name, String code, String neighborhood, String city, String cep) {
        this.name = name;
        this.code = code;
        this.neighborhood = neighborhood;
        this.city = city;
        this.cep = cep;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getCep() {
        return cep;
    }

    public Local createLocalEntity() {
        return new Local(this.name, this.code, this.neighborhood, this.city,this.cep);
    }
}
