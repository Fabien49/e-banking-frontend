package com.fabienit.aeroclubpassion.service.impl;

import com.fabienit.aeroclubpassion.domain.Avion;
import com.fabienit.aeroclubpassion.repository.AvionRepository;
import com.fabienit.aeroclubpassion.service.AvionService;
import com.fabienit.aeroclubpassion.service.dto.AvionDTO;
import com.fabienit.aeroclubpassion.service.mapper.AvionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Avion}.
 */
@Service
@Transactional
public class AvionServiceImpl implements AvionService {

    private final Logger log = LoggerFactory.getLogger(AvionServiceImpl.class);

    private final AvionRepository avionRepository;

    private final AvionMapper avionMapper;

    public AvionServiceImpl(AvionRepository avionRepository, AvionMapper avionMapper) {
        this.avionRepository = avionRepository;
        this.avionMapper = avionMapper;
    }

    @Override
    public AvionDTO save(AvionDTO avionDTO) {
        log.debug("Request to save Avion : {}", avionDTO);
        Avion avion = avionMapper.toEntity(avionDTO);
        avion = avionRepository.save(avion);
        return avionMapper.toDto(avion);
    }

    @Override
    public Optional<AvionDTO> partialUpdate(AvionDTO avionDTO) {
        log.debug("Request to partially update Avion : {}", avionDTO);

        return avionRepository
            .findById(avionDTO.getId())
            .map(existingAvion -> {
                avionMapper.partialUpdate(existingAvion, avionDTO);

                return existingAvion;
            })
            .map(avionRepository::save)
            .map(avionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Avions");
        return avionRepository.findAll(pageable).map(avionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AvionDTO> findOne(Long id) {
        log.debug("Request to get Avion : {}", id);
        return avionRepository.findById(id).map(avionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avion : {}", id);
        avionRepository.deleteById(id);
    }
}
