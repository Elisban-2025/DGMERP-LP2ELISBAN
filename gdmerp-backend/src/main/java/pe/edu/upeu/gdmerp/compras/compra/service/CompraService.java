package pe.edu.upeu.gdmerp.compras.compra.service;

import pe.edu.upeu.gdmerp.compras.compra.dto.CompraRequest;
import pe.edu.upeu.gdmerp.compras.compra.dto.CompraResponse;

import java.util.List;

public interface CompraService {

    CompraResponse registrarCompra(CompraRequest request);

    CompraResponse obtenerCompra(Long idCompra);

    List<CompraResponse> listarTodas();

    void anularCompra(Long idCompra);

    pe.edu.upeu.gdmerp.compras.compra.dto.CompraResponse actualizar(Long id, pe.edu.upeu.gdmerp.compras.compra.dto.CompraRequest request);
    void eliminarLogico(Long id);
    
}

