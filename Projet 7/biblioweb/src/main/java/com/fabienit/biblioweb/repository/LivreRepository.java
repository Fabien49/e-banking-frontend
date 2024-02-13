package com.fabienit.biblioweb.repository;

import com.fabienit.biblioweb.model.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository <Livre, Integer> {

    Livre findById(int id);

    Livre findByCategorieAndTitre(String categorie, String titre);

    Page<Livre> findByTitreContainsAndAuteurContains(String mc, String auteur, Pageable pageable);

}
