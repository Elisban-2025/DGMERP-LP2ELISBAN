package pe.edu.upeu.gdmerp.compras.insumo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "insumo")
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumo")
    private Long idInsumo;
    private String nombre;
    private String codigo;
    @Column(name = "unidad_medida")
    private String unidadMedida;
    
    // Aquí le decimos a Hibernate que acepte exactamente un CHAR(1)
    @Column(columnDefinition = "CHAR(1)")
    private String estado;

    public Long getIdInsumo() { return idInsumo; }
    public void setIdInsumo(Long idInsumo) { this.idInsumo = idInsumo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
