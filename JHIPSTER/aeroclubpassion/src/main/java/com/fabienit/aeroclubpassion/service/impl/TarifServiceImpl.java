package com.fabienit.aeroclubpassion.service.impl;

import com.fabienit.aeroclubpassion.domain.Tarif;
import com.fabienit.aeroclubpassion.repository.TarifRepository;
import com.fabienit.aeroclubpassion.service.TarifService;
import com.fabienit.aeroclubpassion.service.dto.TarifDTO;
import com.fabienit.aeroclubpassion.service.mapper.TarifMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tarif}.
 */
@Service
@Transactional
public class TarifServiceImpl implements TarifService {

    private final Logger log = LoggerFactory.getLogger(TarifServiceImpl.class);

    private final TarifRepository tarifRepository;

    private final TarifMapper tarifMapper;

    public TarifServiceImpl(TarifRepository tarifRepository, TarifMapper tarifMapper) {
        this.tarifRepository = tarifRepository;
        this.tarifMapper = tarifMapper;
    }

    @Override
    public TarifDTO save(TarifDTO tarifDTO) {
        log.debug("Request to save Tarif : {}", tarifDTO);
        Tarif tarif = tarifMapper.toEntity(tarifDTO);
        tarif = tarifRepository.save(tarif);
        return tarifMapper.toDto(tarif);
    }

    @Override
    public Optional<TarifDTO> partialUpdate(TarifDTO tarifDTO) {
        log.debug("Request to partially update Tarif : {}", tarifDTO);

        return tarifRepository
            .findById(tarifDTO.getId())
            .map(existingTarif -> {
                tarifMapper.partialUpdate(existingTarif, tarifDTO);

                return existingTarif;
            })
            .map(tarifRepository::save)
            .map(tarifMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TarifDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tarifs");
        return tarifRepository.findAll(pageable).map(tarifMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TarifDTO> findOne(Long id) {
        log.debug("Request to get Tarif : {}", id);
        return tarifRepository.findById(id).map(tarifMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarif : {}", id);
        tarifRepository.deleteById(id);
    }
}
