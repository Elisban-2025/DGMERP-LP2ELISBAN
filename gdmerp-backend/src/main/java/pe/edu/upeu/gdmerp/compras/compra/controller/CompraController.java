package pe.edu.upeu.gdmerp.compras.compra.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.gdmerp.compras.compra.dto.CompraRequest;
import pe.edu.upeu.gdmerp.compras.compra.dto.CompraResponse;
import pe.edu.upeu.gdmerp.compras.compra.service.CompraService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
@Tag(name = "Compras", description = "API para la gestión de compras")
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar una compra")
    public CompraResponse registrarCompra(@Valid @RequestBody CompraRequest request) {
        return compraService.registrarCompra(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener una compra por ID")
    public CompraResponse obtenerCompra(@PathVariable Long id) {
        return compraService.obtenerCompra(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar todas las compras")
    public List<CompraResponse> listarTodas() {
        return compraService.listarTodas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anularCompraLogico(@PathVariable Long id) {
        compraService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody CompraRequest compraRequest) {
        return ResponseEntity.ok(compraService.actualizar(id, compraRequest));
    }

    // --- NUEVO ENDPOINT DE LA SESIÓN 9 PARA ANULAR ---
    @PatchMapping("/{id}/anular")
    @Operation(summary = "Anular una compra y restaurar stock")
    public ResponseEntity<Void> anularCompraPatch(@PathVariable Long id) {
        compraService.anularCompra(id); // Ejecuta la restauración de stock en tu servicio
        return ResponseEntity.ok().build();
    }
}