package com.fabienIT.friendsEscalade.repository;


import com.fabienIT.friendsEscalade.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface InscriptionRepository extends JpaRepository<Utilisateur, Long> {

}
