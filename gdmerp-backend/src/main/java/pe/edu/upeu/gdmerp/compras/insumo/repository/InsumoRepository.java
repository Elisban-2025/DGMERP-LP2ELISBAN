package pe.edu.upeu.gdmerp.compras.insumo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.gdmerp.compras.insumo.entity.Insumo;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
