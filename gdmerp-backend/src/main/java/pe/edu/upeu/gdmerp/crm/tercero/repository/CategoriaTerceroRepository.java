package pe.edu.upeu.gdmerp.crm.tercero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.gdmerp.crm.tercero.entity.CategoriaTercero;

@Repository
public interface CategoriaTerceroRepository extends JpaRepository<CategoriaTercero, Long> {
}
