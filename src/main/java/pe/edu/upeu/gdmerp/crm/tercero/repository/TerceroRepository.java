package pe.edu.upeu.gdmerp.crm.tercero.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.gdmerp.crm.tercero.entity.Tercero;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerceroRepository extends JpaRepository<Tercero, Long> {

    @Override
    @EntityGraph(attributePaths = {"direcciones", "categoriaTercero"})
    Optional<Tercero> findById(Long id);

    @EntityGraph(attributePaths = {"direcciones", "categoriaTercero"})
    List<Tercero> findByEstado(String estado);

    boolean existsByNumeroDocumento(String numeroDocumento);
}
