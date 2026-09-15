package pe.edu.upeu.gdmerp.compras.compra.service;

import pe.edu.upeu.gdmerp.compras.compra.dto.PagoCompraRequest;

public interface PagoService {

    void registrarPago(PagoCompraRequest request);
}
