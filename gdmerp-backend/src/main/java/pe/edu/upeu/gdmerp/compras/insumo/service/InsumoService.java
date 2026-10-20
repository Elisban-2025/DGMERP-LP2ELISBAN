package pe.edu.upeu.gdmerp.compras.insumo.service;
import org.springframework.stereotype.Service;
import pe.edu.upeu.gdmerp.compras.insumo.entity.Insumo;
import pe.edu.upeu.gdmerp.compras.insumo.repository.InsumoRepository;
import java.util.List;

@Service
public class InsumoService {
    private final InsumoRepository insumoRepository;
    
    public InsumoService(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }
    
    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }
}
