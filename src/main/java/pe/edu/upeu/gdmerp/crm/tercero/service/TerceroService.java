package pe.edu.upeu.gdmerp.crm.tercero.service;

import pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroRequest;
import pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroResponse;

import java.util.List;

public interface TerceroService {

    TerceroResponse crear(TerceroRequest request);

    TerceroResponse obtener(Long id);

    List<TerceroResponse> listarActivos();

    void eliminarLogico(Long id);
}
