package com.fabienit.aeroclubpassion.service.mapper;

import com.fabienit.aeroclubpassion.domain.Atelier;
import com.fabienit.aeroclubpassion.service.dto.AtelierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Atelier} and its DTO {@link AtelierDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AtelierMapper extends EntityMapper<AtelierDTO, Atelier> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AtelierDTO toDtoId(Atelier atelier);
}
