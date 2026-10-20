package pe.edu.upeu.gdmerp.compras.compra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PagoCompraRequest {

    @NotNull(message = "El ID de la cuenta por pagar es obligatorio")
    private Long idCuentaPagar;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    @NotNull(message = "El monto pagado es obligatorio")
    private BigDecimal montoPagado;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private String numeroOperacion;
}
