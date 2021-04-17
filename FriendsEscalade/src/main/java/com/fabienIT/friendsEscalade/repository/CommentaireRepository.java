package com.fabienIT.friendsEscalade.repository;

import com.fabienIT.friendsEscalade.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    Commentaire findAllById(Long id);
    Optional <Commentaire> findById (Long id);
    Commentaire findCommentaireById (Long id);
}
