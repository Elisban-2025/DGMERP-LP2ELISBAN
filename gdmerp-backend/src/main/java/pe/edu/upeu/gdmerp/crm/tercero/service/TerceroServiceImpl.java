package pe.edu.upeu.gdmerp.crm.tercero.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroRequest;
import pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroResponse;
import pe.edu.upeu.gdmerp.crm.tercero.entity.CategoriaTercero;
import pe.edu.upeu.gdmerp.crm.tercero.entity.Tercero;
import pe.edu.upeu.gdmerp.crm.tercero.mapper.TerceroMapper;
import pe.edu.upeu.gdmerp.crm.tercero.repository.CategoriaTerceroRepository;
import pe.edu.upeu.gdmerp.crm.tercero.repository.TerceroRepository;
import pe.edu.upeu.gdmerp.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerceroServiceImpl implements TerceroService {

    private final TerceroRepository terceroRepository;
    private final CategoriaTerceroRepository categoriaTerceroRepository;
    private final TerceroMapper terceroMapper;

    @Override
    @Transactional
    public TerceroResponse crear(TerceroRequest request) {
        if (terceroRepository.existsByNumeroDocumento(request.getNumeroDocumento())) {
            throw new IllegalArgumentException("El documento ya está registrado");
        }

        CategoriaTercero categoria = categoriaTerceroRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + request.getIdCategoria()));

        Tercero tercero = terceroMapper.toEntity(request, categoria);
        tercero.setEstado("ACTIVO");

        if (tercero.getDirecciones() != null) {
            tercero.getDirecciones().forEach(d -> {
                d.setTercero(tercero);
                d.setEstado("ACTIVO");
            });
        }

        Tercero guardado = terceroRepository.save(tercero);
        return terceroMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TerceroResponse obtener(Long id) {
        return terceroRepository.findById(id)
                .map(terceroMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Tercero no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TerceroResponse> listarActivos() {
        return terceroRepository.findAll()
                .stream()
                .map(terceroMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void eliminarLogico(Long id) {
        Tercero tercero = terceroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tercero no encontrado con ID: " + id));

        tercero.setEstado("INACTIVO");
        if (tercero.getDirecciones() != null) {
            tercero.getDirecciones().forEach(d -> d.setEstado("INACTIVO"));
        }

        terceroRepository.save(tercero);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroResponse actualizar(Long id, pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroRequest request) {
        // 1. Verificamos que el tercero exista
        Tercero tercero = terceroRepository.findById(id)
                .orElseThrow(() -> new pe.edu.upeu.gdmerp.exception.ResourceNotFoundException("Tercero no encontrado con ID: " + id));

        // 2. Actualizamos los datos
        tercero.setTipoDocumento(request.getTipoDocumento());
        tercero.setNumeroDocumento(request.getNumeroDocumento());
        tercero.setRazonSocial(request.getRazonSocial());
        tercero.setNombreComercial(request.getNombreComercial());
        tercero.setCelularWhatsapp(request.getCelularWhatsapp());
        tercero.setCorreo(request.getCorreo());
        tercero.setEsCliente(request.getEsCliente());
        tercero.setEsProveedor(request.getEsProveedor());
        //tercero.setIdCategoria(request.getIdCategoria());

        // 3. Guardamos y retornamos convirtiendo a Response
        Tercero terceroActualizado = terceroRepository.save(tercero);
        return terceroMapper.toResponse(terceroActualizado); 
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void eliminar(Long id) {
        if (!terceroRepository.existsById(id)) {
            throw new pe.edu.upeu.gdmerp.exception.ResourceNotFoundException("Tercero no encontrado");
        }
        terceroRepository.deleteById(id);
    }
}
