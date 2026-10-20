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
@Table(name = "CUENTA_POR_PAGAR", schema = "GDMERP_APP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaPorPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUENTA_PAGAR")
    private Long idCuentaPagar;

    @Column(name = "FECHA_VENCIMIENTO_PAGO")
    private LocalDate fechaVencimientoPago;

    @Column(name = "SALDO_PENDIENTE")
    private BigDecimal saldoPendiente;

    @Column(name = "ESTADO")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COMPRA", nullable = false)
    private Compra compra;
}
