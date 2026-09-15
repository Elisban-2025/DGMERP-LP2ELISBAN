package pe.edu.upeu.gdmerp.compras.compra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.upeu.gdmerp.compras.compra.dto.CompraRequest;
import pe.edu.upeu.gdmerp.compras.compra.dto.CompraResponse;
import pe.edu.upeu.gdmerp.compras.compra.dto.CuentaPorPagarResponse;
import pe.edu.upeu.gdmerp.compras.compra.dto.DetalleCompraRequest;
import pe.edu.upeu.gdmerp.compras.compra.dto.DetalleCompraResponse;
import pe.edu.upeu.gdmerp.compras.compra.entity.Compra;
import pe.edu.upeu.gdmerp.compras.compra.entity.CuentaPorPagar;
import pe.edu.upeu.gdmerp.compras.compra.entity.DetalleCompra;

@Mapper(componentModel = "spring")
public interface CompraMapper {

    @Mapping(target = "idCompra", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "igv", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "rutaComprobante", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "creadoPor", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "cuentas", ignore = true)
    @Mapping(target = "detalles", source = "detalles")
    Compra toEntity(CompraRequest request);

    CompraResponse toResponse(Compra compra);

    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "cantidadIngreso", ignore = true)
    @Mapping(target = "subtotalDetalle", ignore = true)
    @Mapping(target = "compra", ignore = true)
    DetalleCompra toDetalleEntity(DetalleCompraRequest request);

    DetalleCompraResponse toDetalleResponse(DetalleCompra detalle);

    CuentaPorPagarResponse toCuentaResponse(CuentaPorPagar cuenta);
}
