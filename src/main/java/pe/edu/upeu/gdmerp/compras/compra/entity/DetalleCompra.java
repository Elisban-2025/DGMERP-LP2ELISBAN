package pe.edu.upeu.gdmerp.compras.compra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DETALLE_COMPRA", schema = "GDMERP_APP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE")
    private Long idDetalle;

    @Column(name = "ID_INSUMO")
    private Long idInsumo;

    @Column(name = "LOTE_PROVEEDOR")
    private String loteProveedor;

    @Column(name = "FECHA_VENCIMIENTO")
    private LocalDate fechaVencimiento;

    @Column(name = "CANTIDAD_COMPRADA")
    private BigDecimal cantidadComprada;

    @Column(name = "FACTOR_CONVERSION")
    private BigDecimal factorConversion;

    @Column(name = "CANTIDAD_INGRESO")
    private BigDecimal cantidadIngreso;

    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;

    @Column(name = "SUBTOTAL_DETALLE")
    private BigDecimal subtotalDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COMPRA", nullable = false)
    private Compra compra;
}
