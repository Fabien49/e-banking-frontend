package com.fabienIT.friendsEscalade.service;


import com.fabienIT.friendsEscalade.model.Commentaire;
import com.fabienIT.friendsEscalade.model.Site;
import com.fabienIT.friendsEscalade.model.Topo;
import com.fabienIT.friendsEscalade.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Set<Topo> topo = siteRepository.findAllById(id).getTopo();
        Set<Commentaire> commentaire = siteRepository.findAllById(id).getCommentaire();
        //mise à jour (récupération) du nom depuis le formulaire d'edition
        dbSite.setNom(site.getNom());
        dbSite.setRegion(site.getRegion());
        dbSite.setNbVoies(site.getNbVoies());
        dbSite.setCotationMin(site.getCotationMin());
        dbSite.setCotationMax(site.getCotationMax());
        dbSite.setDescription(site.getDescription());
        dbSite.setSite_image(site.getSite_image());
        dbSite.setCertifie(site.isCertifie());
        dbSite.setTopo(topo);
        dbSite.setCommentaire(commentaire);
        //mise à jour dans la bdd (sauvegarde)
        siteRepository.save(dbSite);

    }

}




