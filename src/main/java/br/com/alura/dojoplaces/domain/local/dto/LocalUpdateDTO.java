package br.com.alura.dojoplaces.domain.local.dto;
import br.com.alura.dojoplaces.domain.local.Local;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class LocalUpdateDTO {
    private Long id;
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "O código não pode estar em branco")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "O código deve conter apenas letras e números")
    @Column(unique = true )
    private String code;

    @NotBlank(message = "O bairro não pode estar em branco")
    @Size(max = 100)
    private String neighborhood;

    @NotBlank(message = "A cidade não pode estar em branco")
    @Size(max = 100)
    private String city;

    @NotBlank(message = "O Cep não pode estar em branco")
    @Pattern(regexp = "^[0-9]{8}$",message = "O cep deve conter apenas números")
    private String cep;

    private LocalDateTime localDateTime;

    private boolean isDirty;

    public LocalUpdateDTO(Local local) {
    }

    public LocalUpdateDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public  String getCode() {
        return code;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }
    public boolean isDirty(){
        return isDirty;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setCity( String city) {
        this.city = city;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void markAsDirty(){
        this.isDirty=true;
    }

    public static LocalUpdateDTO fromEntity(Local local) {
        LocalUpdateDTO localUpdateDTO = new LocalUpdateDTO(local);
        localUpdateDTO.id = local.getId();
        localUpdateDTO.name = local.getName();
        localUpdateDTO.code = local.getCode();
        localUpdateDTO.neighborhood = local.getNeighborhood();
        localUpdateDTO.city = local.getCity();
        localUpdateDTO.cep = local.getCep();
        return localUpdateDTO;
    }


}
