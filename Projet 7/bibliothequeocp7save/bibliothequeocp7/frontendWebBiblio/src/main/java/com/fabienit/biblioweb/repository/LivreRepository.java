package com.fabienit.biblioweb.repository;

import com.fabienit.biblioweb.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository <Livre, Integer> {

    Livre findById(int id);
}
