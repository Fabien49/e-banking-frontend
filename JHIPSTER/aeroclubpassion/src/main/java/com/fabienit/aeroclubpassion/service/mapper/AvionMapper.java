package com.fabienit.aeroclubpassion.service.mapper;

import com.fabienit.aeroclubpassion.domain.Avion;
import com.fabienit.aeroclubpassion.service.dto.AvionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Avion} and its DTO {@link AvionDTO}.
 */
@Mapper(componentModel = "spring", uses = { AtelierMapper.class, RevisionMapper.class })
public interface AvionMapper extends EntityMapper<AvionDTO, Avion> {
    @Mapping(target = "atelier", source = "atelier", qualifiedByName = "id")
    @Mapping(target = "revision", source = "revision", qualifiedByName = "id")
    AvionDTO toDto(Avion s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AvionDTO toDtoId(Avion avion);
}
