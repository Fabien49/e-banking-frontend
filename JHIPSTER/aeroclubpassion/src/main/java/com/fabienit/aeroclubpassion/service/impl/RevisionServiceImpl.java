package com.fabienit.aeroclubpassion.service.impl;

import com.fabienit.aeroclubpassion.domain.Revision;
import com.fabienit.aeroclubpassion.repository.RevisionRepository;
import com.fabienit.aeroclubpassion.service.RevisionService;
import com.fabienit.aeroclubpassion.service.dto.RevisionDTO;
import com.fabienit.aeroclubpassion.service.mapper.RevisionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Revision}.
 */
@Service
@Transactional
public class RevisionServiceImpl implements RevisionService {

    private final Logger log = LoggerFactory.getLogger(RevisionServiceImpl.class);

    private final RevisionRepository revisionRepository;

    private final RevisionMapper revisionMapper;

    public RevisionServiceImpl(RevisionRepository revisionRepository, RevisionMapper revisionMapper) {
        this.revisionRepository = revisionRepository;
        this.revisionMapper = revisionMapper;
    }

    @Override
    public RevisionDTO save(RevisionDTO revisionDTO) {
        log.debug("Request to save Revision : {}", revisionDTO);
        Revision revision = revisionMapper.toEntity(revisionDTO);
        revision = revisionRepository.save(revision);
        return revisionMapper.toDto(revision);
    }

    @Override
    public Optional<RevisionDTO> partialUpdate(RevisionDTO revisionDTO) {
        log.debug("Request to partially update Revision : {}", revisionDTO);

        return revisionRepository
            .findById(revisionDTO.getId())
            .map(existingRevision -> {
                revisionMapper.partialUpdate(existingRevision, revisionDTO);

                return existingRevision;
            })
            .map(revisionRepository::save)
            .map(revisionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RevisionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Revisions");
        return revisionRepository.findAll(pageable).map(revisionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RevisionDTO> findOne(Long id) {
        log.debug("Request to get Revision : {}", id);
        return revisionRepository.findById(id).map(revisionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Revision : {}", id);
        revisionRepository.deleteById(id);
    }
}
