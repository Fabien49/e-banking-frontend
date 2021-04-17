/*
package com.fabienIT.escaladefriendsp6ocr.repository;


import com.fabienIT.escaladefriendsp6ocr.model.UtilisateurOld;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UtilisateurOld, Long> {


    Page<UtilisateurOld> findByNomContains(String mc, PageRequest of);

    UtilisateurOld findAllById(Long utilisateur);


    @Query("SELECT t FROM Topo t WHERE CONCAT(t.nom, t.proprietaire, t.region, t.nbSites, t.nbVoies, t.cotationMin, t.cotationMax) LIKE %?1%")
    public Page<UtilisateurOld> search(String keyword, Pageable pageable);
}*/
