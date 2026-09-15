package pe.edu.upeu.gdmerp.compras.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponse {

    private Long idCompra;
    private Long idTercero;
    private String tipoComprobante;
    private String serieComprobante;
    private String numeroComprobante;
    private LocalDate fechaEmision;
    private String condicionPago;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal costoFlete;
    private BigDecimal total;
    private String rutaComprobante;
    private String estado;
    private String creadoPor;
    private LocalDateTime fechaCreacion;
    private List<DetalleCompraResponse> detalles;
    private List<CuentaPorPagarResponse> cuentas;
}
