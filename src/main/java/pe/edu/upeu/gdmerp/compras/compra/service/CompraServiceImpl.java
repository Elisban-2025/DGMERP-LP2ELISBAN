package pe.edu.upeu.gdmerp.compras.compra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.gdmerp.compras.compra.dto.CompraRequest;
import pe.edu.upeu.gdmerp.compras.compra.dto.CompraResponse;
import pe.edu.upeu.gdmerp.compras.compra.entity.Compra;
import pe.edu.upeu.gdmerp.compras.compra.entity.CuentaPorPagar;
import pe.edu.upeu.gdmerp.compras.compra.entity.DetalleCompra;
import pe.edu.upeu.gdmerp.compras.compra.mapper.CompraMapper;
import pe.edu.upeu.gdmerp.compras.compra.repository.CompraRepository;
import pe.edu.upeu.gdmerp.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final CompraMapper compraMapper;

    @Override
    @Transactional
    public CompraResponse registrarCompra(CompraRequest request) {
        // a) Mapea el request a la entidad Compra
        Compra compra = compraMapper.toEntity(request);

        // b) Inicializa variables
        BigDecimal subtotal = BigDecimal.ZERO;

        // c) Itera los detalles
        if (compra.getDetalles() != null) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                BigDecimal cantidadIngreso = detalle.getCantidadComprada().multiply(detalle.getFactorConversion());
                detalle.setCantidadIngreso(cantidadIngreso);

                BigDecimal subtotalDetalle = cantidadIngreso.multiply(detalle.getPrecioUnitario());
                detalle.setSubtotalDetalle(subtotalDetalle);

                subtotal = subtotal.add(subtotalDetalle);
                detalle.setCompra(compra);
            }
        }

        // d) Calcula IGV (18%)
        BigDecimal igv = subtotal.multiply(new BigDecimal("0.18"));

        // e) Calcula Total
        BigDecimal total = subtotal.add(igv).add(request.getCostoFlete());

        // f) Asigna los valores a la cabecera
        compra.setSubtotal(subtotal);
        compra.setIgv(igv);
        compra.setTotal(total);
        compra.setEstado("REGISTRADA");
        compra.setFechaCreacion(LocalDateTime.now());

        // g) Genera la Cuenta por Pagar
        LocalDate fechaVencimientoPago = request.getFechaEmision() != null
                ? request.getFechaEmision().plusDays(30)
                : LocalDate.now().plusDays(30);

        CuentaPorPagar cuenta = CuentaPorPagar.builder()
                .fechaVencimientoPago(fechaVencimientoPago)
                .saldoPendiente(total)
                .estado("PENDIENTE")
                .compra(compra)
                .build();

        if (compra.getCuentas() == null) {
            compra.setCuentas(new ArrayList<>());
        }
        compra.getCuentas().add(cuenta);

        // h) Guarda en base de datos
        Compra compraGuardada = compraRepository.save(compra);

        // i) Retorna el response mapeado
        return compraMapper.toResponse(compraGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public CompraResponse obtenerCompra(Long idCompra) {
        return compraRepository.findById(idCompra)
                .map(compraMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con ID: " + idCompra));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraResponse> listarTodas() {
        return compraRepository.findAll().stream()
                .map(compraMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void anularCompra(Long idCompra) {
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con ID: " + idCompra));

        compra.setEstado("ANULADA");

        if (compra.getCuentas() != null) {
            for (CuentaPorPagar cuenta : compra.getCuentas()) {
                cuenta.setEstado("ANULADA");
            }
        }

        compraRepository.save(compra);
    }
}
