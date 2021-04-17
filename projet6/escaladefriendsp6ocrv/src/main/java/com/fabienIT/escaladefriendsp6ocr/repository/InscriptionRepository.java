package com.fabienIT.escaladefriendsp6ocr.repository;


import com.fabienIT.escaladefriendsp6ocr.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface InscriptionRepository extends JpaRepository<Utilisateur, Long> {

}
