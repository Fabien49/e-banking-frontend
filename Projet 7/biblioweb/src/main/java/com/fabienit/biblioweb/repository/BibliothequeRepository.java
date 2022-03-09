package com.fabienit.biblioapi.repository;

import com.fabienit.biblioapi.model.Bibliotheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliothequeRepository extends JpaRepository<Bibliotheque, Integer> {

    Bibliotheque findById(int id);
}
