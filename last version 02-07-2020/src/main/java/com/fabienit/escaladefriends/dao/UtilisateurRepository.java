package com.fabienit.escaladefriends.dao;

import com.fabienit.escaladefriends.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UtilisateurRepository extends JpaRepository <Utilisateur, Long>{

    public Page<Utilisateur> findByNomContains(String mc, Pageable pageable);


}