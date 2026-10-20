package pe.edu.upeu.gdmerp.compras.compra.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.gdmerp.compras.compra.dto.PagoCompraRequest;
import pe.edu.upeu.gdmerp.compras.compra.service.PagoService;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "API para registrar pagos y amortizaciones de deudas")
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar un pago a una cuenta por pagar")
    public void registrarPago(@Valid @RequestBody PagoCompraRequest request) {
        pagoService.registrarPago(request);
    }
}
