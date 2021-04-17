package com.fabienIT.escaladefriendsp6ocr.service;


import com.fabienIT.escaladefriendsp6ocr.model.Commentaire;
import com.fabienIT.escaladefriendsp6ocr.model.Site;
import com.fabienIT.escaladefriendsp6ocr.model.Topo;
import com.fabienIT.escaladefriendsp6ocr.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SiteService {

    @Autowired
    SiteRepository siteRepository;

    public void ajouter(Site site) {
        siteRepository.save(site);
    }


    public Site findSiteById(Long id) {
        return (Site) siteRepository.findAllById(id);
    }

    public Optional<Site> findById(Long id) {
        return siteRepository.findById(id);
    }

    public void effacer (Long id) {
        siteRepository.deleteById(id);
    }

    public Site findByNom(String nom){return  siteRepository.findByNom(nom);}



    public void updateSite(Site site) {
        //recuparation du topo en base via l'id
        Long id = site.getId();
        System.out.println("l'Id du site est : " + id);
        Site dbSite = siteRepository.findById(id).get();
        Set<Commentaire> commentaire = siteRepository.findAllById(id).getCommentaire();
        //mise à jour (récupération) du nom depuis le formulaire d'edition
        dbSite.getId();
        dbSite.setNom(site.getNom());
        dbSite.setRegion(site.getRegion());
        dbSite.setNbVoies(site.getNbVoies());
        dbSite.setCotationMin(site.getCotationMin());
        dbSite.setCotationMax(site.getCotationMax());
        dbSite.setDescription(site.getDescription());
        dbSite.setSite_image(site.getSite_image());
        dbSite.setCertifie(site.isCertifie());
        dbSite.setCommentaire(commentaire);
        //mise à jour dans la bdd (sauvegarde)
        siteRepository.save(dbSite);

    }

    public void updateSiteCerfifie (Site site) {
        Long id = site.getId();
        System.out.println("l'Id du site est : " + id);
        Site dbSite = siteRepository.findById(id).get();
        //mise à jour (récupération) du nom depuis le formulaire d'edition

        dbSite.setNom(site.getNom());
        dbSite.setRegion(site.getRegion());
        dbSite.setNbVoies(site.getNbVoies());
        dbSite.setCotationMin(site.getCotationMin());
        dbSite.setCotationMax(site.getCotationMax());
        dbSite.setDescription(site.getDescription());
        dbSite.setSite_image(site.getSite_image());
        dbSite.setCertifie(site.isCertifie());
        dbSite.setCommentaire(site.getCommentaire());
        //mise à jour dans la bdd (sauvegarde)
        siteRepository.save(dbSite);
    }

}




