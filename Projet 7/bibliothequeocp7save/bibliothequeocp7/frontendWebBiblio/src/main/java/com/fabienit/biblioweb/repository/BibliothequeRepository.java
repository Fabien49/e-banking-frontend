package com.fabienit.biblioweb.repository;

import com.fabienit.biblioweb.model.Bibliotheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliothequeRepository extends JpaRepository<Bibliotheque, Integer> {

    Bibliotheque findById(int id);
}
