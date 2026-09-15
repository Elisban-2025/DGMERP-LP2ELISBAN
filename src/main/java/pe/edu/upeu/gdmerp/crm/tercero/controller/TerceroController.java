package pe.edu.upeu.gdmerp.crm.tercero.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroRequest;
import pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroResponse;
import pe.edu.upeu.gdmerp.crm.tercero.service.TerceroService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/terceros")
@RequiredArgsConstructor
@Tag(name = "Terceros", description = "API para la gestión de terceros (clientes y proveedores)")
public class TerceroController {

    private final TerceroService terceroService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar un nuevo tercero")
    public TerceroResponse crear(@Valid @RequestBody TerceroRequest request) {
        return terceroService.crear(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener un tercero por ID")
    public TerceroResponse obtener(@PathVariable Long id) {
        return terceroService.obtener(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar terceros")
    public List<TerceroResponse> listarActivos() {
        return terceroService.listarActivos();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminación lógica de un tercero por ID")
    public void eliminarLogico(@PathVariable Long id) {
        terceroService.eliminarLogico(id);
    }
}
