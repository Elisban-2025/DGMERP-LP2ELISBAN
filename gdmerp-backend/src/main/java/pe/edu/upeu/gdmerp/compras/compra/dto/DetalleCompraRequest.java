package pe.edu.upeu.gdmerp.compras.compra.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DetalleCompraRequest {

    @NotNull(message = "El insumo es obligatorio")
    private Long idInsumo;

    private String loteProveedor;

    private LocalDate fechaVencimiento;

    @NotNull(message = "La cantidad comprada es obligatoria")
    @Min(value = 1, message = "La cantidad comprada debe ser al menos 1")
    private BigDecimal cantidadComprada;

    @NotNull(message = "El factor de conversión es obligatorio")
    @Min(value = 1, message = "El factor de conversión debe ser al menos 1")
    private BigDecimal factorConversion;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    private BigDecimal precioUnitario;
}
