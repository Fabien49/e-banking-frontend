package com.fabienit.aeroclubpassion.service.impl;

import com.fabienit.aeroclubpassion.domain.Aeroclub;
import com.fabienit.aeroclubpassion.repository.AeroclubRepository;
import com.fabienit.aeroclubpassion.service.AeroclubService;
import com.fabienit.aeroclubpassion.service.dto.AeroclubDTO;
import com.fabienit.aeroclubpassion.service.mapper.AeroclubMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Aeroclub}.
 */
@Service
@Transactional
public class AeroclubServiceImpl implements AeroclubService {

    private final Logger log = LoggerFactory.getLogger(AeroclubServiceImpl.class);

    private final AeroclubRepository aeroclubRepository;

    private final AeroclubMapper aeroclubMapper;

    public AeroclubServiceImpl(AeroclubRepository aeroclubRepository, AeroclubMapper aeroclubMapper) {
        this.aeroclubRepository = aeroclubRepository;
        this.aeroclubMapper = aeroclubMapper;
    }

    @Override
    public AeroclubDTO save(AeroclubDTO aeroclubDTO) {
        log.debug("Request to save Aeroclub : {}", aeroclubDTO);
        Aeroclub aeroclub = aeroclubMapper.toEntity(aeroclubDTO);
        aeroclub = aeroclubRepository.save(aeroclub);
        return aeroclubMapper.toDto(aeroclub);
    }

    @Override
    public Optional<AeroclubDTO> partialUpdate(AeroclubDTO aeroclubDTO) {
        log.debug("Request to partially update Aeroclub : {}", aeroclubDTO);

        return aeroclubRepository
            .findById(aeroclubDTO.getId())
            .map(existingAeroclub -> {
                aeroclubMapper.partialUpdate(existingAeroclub, aeroclubDTO);

                return existingAeroclub;
            })
            .map(aeroclubRepository::save)
            .map(aeroclubMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AeroclubDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Aeroclubs");
        return aeroclubRepository.findAll(pageable).map(aeroclubMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AeroclubDTO> findOne(Long id) {
        log.debug("Request to get Aeroclub : {}", id);
        return aeroclubRepository.findById(id).map(aeroclubMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aeroclub : {}", id);
        aeroclubRepository.deleteById(id);
    }
}
