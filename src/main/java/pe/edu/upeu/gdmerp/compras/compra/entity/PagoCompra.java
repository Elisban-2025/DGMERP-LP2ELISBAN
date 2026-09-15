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
import java.time.LocalDateTime;

@Entity
@Table(name = "PAGO_COMPRA", schema = "GDMERP_APP")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAGO")
    private Long idPago;

    @Column(name = "FECHA_PAGO")
    private LocalDate fechaPago;

    @Column(name = "MONTO_PAGADO")
    private BigDecimal montoPagado;

    @Column(name = "METODO_PAGO")
    private String metodoPago;

    @Column(name = "NUMERO_OPERACION")
    private String numeroOperacion;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CUENTA_PAGAR")
    private CuentaPorPagar cuentaPorPagar;
}
