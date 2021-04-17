package com.fabienit.escaladefriends.dao;

import com.fabienit.escaladefriends.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface InscriptionRepository extends JpaRepository <Utilisateur, Long>{

}
