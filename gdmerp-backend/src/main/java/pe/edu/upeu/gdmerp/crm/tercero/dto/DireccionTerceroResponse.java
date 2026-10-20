package pe.edu.upeu.gdmerp.crm.tercero.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DireccionTerceroResponse {

    private Long idDireccion;
    private String tipoDireccion;
    private String direccionCompleta;
    private String referencia;
    private String estado;
}
