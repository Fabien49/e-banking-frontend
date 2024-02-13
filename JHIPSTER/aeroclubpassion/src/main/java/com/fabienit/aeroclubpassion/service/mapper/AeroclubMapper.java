package com.fabienit.aeroclubpassion.service.mapper;

import com.fabienit.aeroclubpassion.domain.Aeroclub;
import com.fabienit.aeroclubpassion.service.dto.AeroclubDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aeroclub} and its DTO {@link AeroclubDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AeroclubMapper extends EntityMapper<AeroclubDTO, Aeroclub> {}
