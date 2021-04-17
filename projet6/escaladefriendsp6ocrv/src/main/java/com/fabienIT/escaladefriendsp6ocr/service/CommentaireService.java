package com.fabienIT.escaladefriendsp6ocr.service;


import com.fabienIT.escaladefriendsp6ocr.model.Commentaire;
import com.fabienIT.escaladefriendsp6ocr.model.Topo;
import com.fabienIT.escaladefriendsp6ocr.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {

    @Autowired
    CommentaireRepository commentaireRepository;

    public List<Commentaire> findAllCom (){return commentaireRepository.findAll(); }

    public void ajouterCom (Commentaire commentaire){
        commentaireRepository.save(commentaire);
    }

    public Commentaire findAllById(Long id) {
        return commentaireRepository.findAllById(id);
    }

    public Optional <Commentaire>findById(Long id) { return  commentaireRepository.findById(id);
    }

    public void effacer(Long id) {
        commentaireRepository.deleteById(id);
    }

    public Commentaire findCommentaireById(Long id) {
        return commentaireRepository.findCommentaireById(id);
    }

    public void updateCommentaire(Commentaire commentaire) {
        //recuparation du commentaire en base via l'id
        Long id = commentaire.getId();
        Commentaire dbCommentaire = commentaireRepository.findById(id).get();
        //mise à jour (récupération) du nom depuis le formulaire d'edition
        dbCommentaire.setComDate(commentaire.getComDate());
        dbCommentaire.setCom(commentaire.getCom());
        dbCommentaire.setSite(commentaire.getSite());
        dbCommentaire.setUser(commentaire.getUser());
        //mise à jour dans la bdd (sauvegarde)
        commentaireRepository.save(dbCommentaire);
    }

}
