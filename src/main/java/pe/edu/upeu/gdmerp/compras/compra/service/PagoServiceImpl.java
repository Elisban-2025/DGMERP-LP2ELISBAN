package pe.edu.upeu.gdmerp.compras.compra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.gdmerp.compras.compra.dto.PagoCompraRequest;
import pe.edu.upeu.gdmerp.compras.compra.entity.CuentaPorPagar;
import pe.edu.upeu.gdmerp.compras.compra.entity.PagoCompra;
import pe.edu.upeu.gdmerp.compras.compra.repository.CuentaPorPagarRepository;
import pe.edu.upeu.gdmerp.compras.compra.repository.PagoCompraRepository;
import pe.edu.upeu.gdmerp.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final CuentaPorPagarRepository cuentaPorPagarRepository;
    private final PagoCompraRepository pagoCompraRepository;

    @Override
    @Transactional
    public void registrarPago(PagoCompraRequest request) {
        // a) Busca la cuenta por request.getIdCuentaPagar() (Lanza RuntimeException si no existe)
        CuentaPorPagar cuenta = cuentaPorPagarRepository.findById(request.getIdCuentaPagar())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta por pagar no encontrada con ID: " + request.getIdCuentaPagar()));

        // b) Valida si request.getMontoPagado() es mayor al saldo pendiente de la cuenta. Si es mayor, lanza IllegalArgumentException
        if (request.getMontoPagado().compareTo(cuenta.getSaldoPendiente()) > 0) {
            throw new IllegalArgumentException("El monto supera el saldo pendiente");
        }

        // c) Resta el monto al saldo pendiente
        cuenta.setSaldoPendiente(cuenta.getSaldoPendiente().subtract(request.getMontoPagado()));

        // d) Si el saldo llega a 0, cambia el estado de la cuenta
        if (cuenta.getSaldoPendiente().compareTo(BigDecimal.ZERO) == 0) {
            cuenta.setEstado("PAGADO");
        }

        // e) Construye el PagoCompra usando builder
        PagoCompra pagoCompra = PagoCompra.builder()
                .fechaPago(request.getFechaPago())
                .montoPagado(request.getMontoPagado())
                .metodoPago(request.getMetodoPago())
                .numeroOperacion(request.getNumeroOperacion())
                .estado("APLICADO")
                .fechaCreacion(LocalDateTime.now())
                .cuentaPorPagar(cuenta)
                .build();

        // f) Guarda el pago en su repositorio y guarda la cuenta actualizada en su repositorio
        pagoCompraRepository.save(pagoCompra);
        cuentaPorPagarRepository.save(cuenta);
    }
}
