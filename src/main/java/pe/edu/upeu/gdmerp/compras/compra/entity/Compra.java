package pe.edu.upeu.gdmerp.compras.compra.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPRA", schema = "GDMERP_APP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMPRA")
    private Long idCompra;

    @Column(name = "ID_TERCERO")
    private Long idTercero;

    @Column(name = "TIPO_COMPROBANTE")
    private String tipoComprobante;

    @Column(name = "SERIE_COMPROBANTE")
    private String serieComprobante;

    @Column(name = "NUMERO_COMPROBANTE")
    private String numeroComprobante;

    @Column(name = "FECHA_EMISION")
    private LocalDate fechaEmision;

    @Column(name = "CONDICION_PAGO")
    private String condicionPago;

    @Column(name = "SUBTOTAL")
    private BigDecimal subtotal;

    @Column(name = "IGV")
    private BigDecimal igv;

    @Column(name = "COSTO_FLETE")
    private BigDecimal costoFlete;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Column(name = "RUTA_COMPROBANTE")
    private String rutaComprobante;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "CREADO_POR")
    private String creadoPor;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Builder.Default
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCompra> detalles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuentaPorPagar> cuentas = new ArrayList<>();

    public void addDetalle(DetalleCompra detalle) {
        detalles.add(detalle);
        detalle.setCompra(this);
    }

    public void removeDetalle(DetalleCompra detalle) {
        detalles.remove(detalle);
        detalle.setCompra(null);
    }

    public void addCuenta(CuentaPorPagar cuenta) {
        cuentas.add(cuenta);
        cuenta.setCompra(this);
    }

    public void removeCuenta(CuentaPorPagar cuenta) {
        cuentas.remove(cuenta);
        cuenta.setCompra(null);
    }
}
