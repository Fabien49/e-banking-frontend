package com.fabienit.aeroclubpassion.service.impl;

import com.fabienit.aeroclubpassion.domain.UserRegistered;
import com.fabienit.aeroclubpassion.repository.UserRegisteredRepository;
import com.fabienit.aeroclubpassion.service.UserRegisteredService;
import com.fabienit.aeroclubpassion.service.dto.UserRegisteredDTO;
import com.fabienit.aeroclubpassion.service.mapper.UserRegisteredMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserRegistered}.
 */
@Service
@Transactional
public class UserRegisteredServiceImpl implements UserRegisteredService {

    private final Logger log = LoggerFactory.getLogger(UserRegisteredServiceImpl.class);

    private final UserRegisteredRepository userRegisteredRepository;

    private final UserRegisteredMapper userRegisteredMapper;

    public UserRegisteredServiceImpl(UserRegisteredRepository userRegisteredRepository, UserRegisteredMapper userRegisteredMapper) {
        this.userRegisteredRepository = userRegisteredRepository;
        this.userRegisteredMapper = userRegisteredMapper;
    }

    @Override
    public UserRegisteredDTO save(UserRegisteredDTO userRegisteredDTO) {
        log.debug("Request to save UserRegistered : {}", userRegisteredDTO);
        UserRegistered userRegistered = userRegisteredMapper.toEntity(userRegisteredDTO);
        userRegistered = userRegisteredRepository.save(userRegistered);
        return userRegisteredMapper.toDto(userRegistered);
    }

    @Override
    public Optional<UserRegisteredDTO> partialUpdate(UserRegisteredDTO userRegisteredDTO) {
        log.debug("Request to partially update UserRegistered : {}", userRegisteredDTO);

        return userRegisteredRepository
            .findById(userRegisteredDTO.getId())
            .map(existingUserRegistered -> {
                userRegisteredMapper.partialUpdate(existingUserRegistered, userRegisteredDTO);

                return existingUserRegistered;
            })
            .map(userRegisteredRepository::save)
            .map(userRegisteredMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserRegisteredDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRegistereds");
        return userRegisteredRepository.findAll(pageable).map(userRegisteredMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserRegisteredDTO> findOne(Long id) {
        log.debug("Request to get UserRegistered : {}", id);
        return userRegisteredRepository.findById(id).map(userRegisteredMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRegistered : {}", id);
        userRegisteredRepository.deleteById(id);
    }
}
