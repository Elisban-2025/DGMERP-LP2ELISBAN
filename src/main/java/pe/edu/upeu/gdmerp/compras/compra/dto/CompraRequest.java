package pe.edu.upeu.gdmerp.compras.compra.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CompraRequest {

    @NotNull(message = "El tercero es obligatorio")
    private Long idTercero;

    @NotBlank(message = "El tipo de comprobante es obligatorio")
    private String tipoComprobante;

    private String serieComprobante;

    @NotBlank(message = "El número de comprobante es obligatorio")
    private String numeroComprobante;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fechaEmision;

    @NotBlank(message = "La condición de pago es obligatoria")
    private String condicionPago;

    @NotNull(message = "El costo de flete es obligatorio")
    @Min(value = 0, message = "El costo de flete no puede ser negativo")
    private BigDecimal costoFlete;

    @Valid
    @NotEmpty(message = "Debe registrar al menos un detalle de compra")
    private List<DetalleCompraRequest> detalles;
}
