package com.fabienit.aeroclubpassion.service.mapper;

import com.fabienit.aeroclubpassion.domain.Revision;
import com.fabienit.aeroclubpassion.service.dto.RevisionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Revision} and its DTO {@link RevisionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RevisionMapper extends EntityMapper<RevisionDTO, Revision> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RevisionDTO toDtoId(Revision revision);
}
