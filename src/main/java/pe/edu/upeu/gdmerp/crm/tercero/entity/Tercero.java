package pe.edu.upeu.gdmerp.crm.tercero.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TERCERO", schema = "GDMERP_APP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tercero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TERCERO")
    private Long idTercero;

    @Column(name = "TIPO_DOCUMENTO", nullable = false, length = 20)
    private String tipoDocumento;

    @Column(name = "NUMERO_DOCUMENTO", nullable = false, length = 25, unique = true)
    private String numeroDocumento;

    @Column(name = "RAZON_SOCIAL", nullable = false, length = 255)
    private String razonSocial;

    @Column(name = "NOMBRE_COMERCIAL", length = 255)
    private String nombreComercial;

    @Column(name = "CELULAR_WHATSAPP", length = 20)
    private String celularWhatsapp;

    @Column(name = "CORREO", length = 150)
    private String correo;

    @Column(name = "ES_CLIENTE", nullable = false)
    private Integer esCliente;

    @Column(name = "ES_PROVEEDOR", nullable = false)
    private Integer esProveedor;

    @Column(name = "FECHA_ANIVERSARIO")
    private LocalDate fechaAniversario;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;

    @Column(name = "ESTADO", length = 20)
    private String estado;

    @Column(name = "MOTIVO_BLOQUEO", length = 255)
    private String motivoBloqueo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA")
    private CategoriaTercero categoriaTercero;

    @Builder.Default
    @OneToMany(mappedBy = "tercero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DireccionTercero> direcciones = new ArrayList<>();

    public void addDireccion(DireccionTercero direccion) {
        direcciones.add(direccion);
        direccion.setTercero(this);
    }

    public void removeDireccion(DireccionTercero direccion) {
        direcciones.remove(direccion);
        direccion.setTercero(null);
    }
}
