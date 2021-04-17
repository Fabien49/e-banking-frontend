package com.fabienIT.escaladefriendsp6ocr.repository;

import com.fabienIT.escaladefriendsp6ocr.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    Commentaire findAllById(Long id);
    Optional<Commentaire> findById (Long id);
    Commentaire findCommentaireById (Long id);
}
