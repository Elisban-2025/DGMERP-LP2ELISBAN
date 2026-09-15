package pe.edu.upeu.gdmerp.compras.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompraResponse {

    private Long idDetalle;
    private Long idInsumo;
    private String loteProveedor;
    private LocalDate fechaVencimiento;
    private BigDecimal cantidadComprada;
    private BigDecimal factorConversion;
    private BigDecimal cantidadIngreso;
    private BigDecimal precioUnitario;
    private BigDecimal subtotalDetalle;
}
