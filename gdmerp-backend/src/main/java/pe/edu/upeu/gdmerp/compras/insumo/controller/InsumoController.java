package pe.edu.upeu.gdmerp.compras.insumo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.gdmerp.compras.insumo.entity.Insumo;
import pe.edu.upeu.gdmerp.compras.insumo.service.InsumoService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/insumos")
public class InsumoController {
    private final InsumoService insumoService;
    
    public InsumoController(InsumoService insumoService) {
        this.insumoService = insumoService;
    }

    @GetMapping
    public ResponseEntity<List<Insumo>> listar() {
        return ResponseEntity.ok(insumoService.listar());
    }
}
