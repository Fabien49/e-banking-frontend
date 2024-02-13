package com.fabienit.aeroclubpassion.service.mapper;

import com.fabienit.aeroclubpassion.domain.UserRegistered;
import com.fabienit.aeroclubpassion.service.dto.UserRegisteredDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserRegistered} and its DTO {@link UserRegisteredDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface UserRegisteredMapper extends EntityMapper<UserRegisteredDTO, UserRegistered> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    UserRegisteredDTO toDto(UserRegistered s);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    UserRegisteredDTO toDtoNom(UserRegistered userRegistered);
}
