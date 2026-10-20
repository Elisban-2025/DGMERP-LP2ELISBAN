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
public class CuentaPorPagarResponse {

    private Long idCuentaPagar;
    private LocalDate fechaVencimientoPago;
    private BigDecimal saldoPendiente;
    private String estado;
}
