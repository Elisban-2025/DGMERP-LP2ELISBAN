package pe.edu.upeu.gdmerp.crm.tercero.entity;

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

@Entity
@Table(name = "DIRECCION_TERCERO", schema = "GDMERP_APP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionTercero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIRECCION")
    private Long idDireccion;

    @Column(name = "TIPO_DIRECCION", length = 50)
    private String tipoDireccion;

    @Column(name = "DIRECCION_COMPLETA", nullable = false, length = 300)
    private String direccionCompleta;

    @Column(name = "REFERENCIA", length = 300)
    private String referencia;

    @Column(name = "ESTADO", length = 20)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TERCERO", nullable = false)
    private Tercero tercero;
}
