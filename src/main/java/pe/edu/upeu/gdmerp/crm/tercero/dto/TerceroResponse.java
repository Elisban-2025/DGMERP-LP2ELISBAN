package pe.edu.upeu.gdmerp.crm.tercero.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerceroResponse {

    private Long idTercero;
    private String tipoDocumento;
    private String numeroDocumento;
    private String razonSocial;
    private String nombreComercial;
    private String celularWhatsapp;
    private String correo;
    private Integer esCliente;
    private Integer esProveedor;
    private Long idCategoria;
    private String estado;
    private List<DireccionTerceroResponse> direcciones;
}
