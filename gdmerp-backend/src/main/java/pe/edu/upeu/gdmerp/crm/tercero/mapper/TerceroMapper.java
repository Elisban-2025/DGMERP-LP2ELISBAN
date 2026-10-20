package pe.edu.upeu.gdmerp.crm.tercero.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.upeu.gdmerp.crm.tercero.dto.DireccionTerceroRequest;
import pe.edu.upeu.gdmerp.crm.tercero.dto.DireccionTerceroResponse;
import pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroRequest;
import pe.edu.upeu.gdmerp.crm.tercero.dto.TerceroResponse;
import pe.edu.upeu.gdmerp.crm.tercero.entity.CategoriaTercero;
import pe.edu.upeu.gdmerp.crm.tercero.entity.DireccionTercero;
import pe.edu.upeu.gdmerp.crm.tercero.entity.Tercero;

@Mapper(componentModel = "spring")
public interface TerceroMapper {

    @Mapping(target = "idTercero", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "motivoBloqueo", ignore = true)
    @Mapping(target = "fechaAniversario", ignore = true)
    @Mapping(target = "observaciones", ignore = true)
    @Mapping(target = "categoriaTercero", source = "categoriaTercero")
    @Mapping(target = "direcciones", source = "request.direcciones")
    Tercero toEntity(TerceroRequest request, CategoriaTercero categoriaTercero);

    @Mapping(target = "idCategoria", source = "categoriaTercero.idCategoria")
    @Mapping(target = "direcciones", source = "direcciones")
    TerceroResponse toResponse(Tercero tercero);

    @Mapping(target = "idDireccion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "tercero", ignore = true)
    DireccionTercero toDireccionEntity(DireccionTerceroRequest request);

    DireccionTerceroResponse toDireccionResponse(DireccionTercero direccion);
}
