package pe.edu.upeu.gdmerp.crm.tercero.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TerceroRequest {

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "La razón social es obligatoria")
    private String razonSocial;

    private String nombreComercial;

    private String celularWhatsapp;

    private String correo;

    @NotNull(message = "Debe especificar si es cliente")
    private Integer esCliente;

    @NotNull(message = "Debe especificar si es proveedor")
    private Integer esProveedor;

    private Long idCategoria;

    @Valid
    @NotEmpty(message = "Debe registrar al menos una dirección")
    private List<DireccionTerceroRequest> direcciones;
}
