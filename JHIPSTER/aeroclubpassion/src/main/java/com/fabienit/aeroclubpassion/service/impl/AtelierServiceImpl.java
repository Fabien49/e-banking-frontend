package com.fabienit.aeroclubpassion.service.impl;

import com.fabienit.aeroclubpassion.domain.Atelier;
import com.fabienit.aeroclubpassion.repository.AtelierRepository;
import com.fabienit.aeroclubpassion.service.AtelierService;
import com.fabienit.aeroclubpassion.service.dto.AtelierDTO;
import com.fabienit.aeroclubpassion.service.mapper.AtelierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Atelier}.
 */
@Service
@Transactional
public class AtelierServiceImpl implements AtelierService {

    private final Logger log = LoggerFactory.getLogger(AtelierServiceImpl.class);

    private final AtelierRepository atelierRepository;

    private final AtelierMapper atelierMapper;

    public AtelierServiceImpl(AtelierRepository atelierRepository, AtelierMapper atelierMapper) {
        this.atelierRepository = atelierRepository;
        this.atelierMapper = atelierMapper;
    }

    @Override
    public AtelierDTO save(AtelierDTO atelierDTO) {
        log.debug("Request to save Atelier : {}", atelierDTO);
        Atelier atelier = atelierMapper.toEntity(atelierDTO);
        atelier = atelierRepository.save(atelier);
        return atelierMapper.toDto(atelier);
    }

    @Override
    public Optional<AtelierDTO> partialUpdate(AtelierDTO atelierDTO) {
        log.debug("Request to partially update Atelier : {}", atelierDTO);

        return atelierRepository
            .findById(atelierDTO.getId())
            .map(existingAtelier -> {
                atelierMapper.partialUpdate(existingAtelier, atelierDTO);

                return existingAtelier;
            })
            .map(atelierRepository::save)
            .map(atelierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtelierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ateliers");
        return atelierRepository.findAll(pageable).map(atelierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AtelierDTO> findOne(Long id) {
        log.debug("Request to get Atelier : {}", id);
        return atelierRepository.findById(id).map(atelierMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Atelier : {}", id);
        atelierRepository.deleteById(id);
    }
}
