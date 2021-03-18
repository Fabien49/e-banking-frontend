package com.macrosoftas.testHibernate.repository;


import com.macrosoftas.testHibernate.model.Eleve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface EleveRepository extends JpaRepository<Eleve, Long> {

    public Page<Eleve> findByNomContains(String mc, Pageable pageable);


}