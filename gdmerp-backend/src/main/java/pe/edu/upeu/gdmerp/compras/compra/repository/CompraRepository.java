package pe.edu.upeu.gdmerp.compras.compra.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.gdmerp.compras.compra.entity.Compra;

import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Override
    @EntityGraph(attributePaths = {"detalles", "cuentas"})
    Optional<Compra> findById(Long id);
}
