package com.fabienit.aeroclubpassion.service.mapper;

import com.fabienit.aeroclubpassion.domain.Reservation;
import com.fabienit.aeroclubpassion.service.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring", uses = { AvionMapper.class, UserRegisteredMapper.class })
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {
    @Mapping(target = "avions", source = "avions", qualifiedByName = "id")
    @Mapping(target = "userRegistereds", source = "userRegistereds", qualifiedByName = "nom")
    ReservationDTO toDto(Reservation s);
}
