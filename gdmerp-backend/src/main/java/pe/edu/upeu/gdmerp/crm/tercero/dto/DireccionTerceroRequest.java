package pe.edu.upeu.gdmerp.crm.tercero.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionTerceroRequest {

    @NotBlank(message = "El tipo de dirección es obligatorio")
    private String tipoDireccion;

    @NotBlank(message = "La dirección completa es obligatoria")
    private String direccionCompleta;

    private String referencia;
}
