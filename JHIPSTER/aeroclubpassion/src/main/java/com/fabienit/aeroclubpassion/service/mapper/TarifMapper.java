package com.fabienit.aeroclubpassion.service.mapper;

import com.fabienit.aeroclubpassion.domain.Tarif;
import com.fabienit.aeroclubpassion.service.dto.TarifDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarif} and its DTO {@link TarifDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TarifMapper extends EntityMapper<TarifDTO, Tarif> {}
