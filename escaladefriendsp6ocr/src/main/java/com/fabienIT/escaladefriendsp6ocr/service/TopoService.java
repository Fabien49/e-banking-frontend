package com.fabienIT.escaladefriendsp6ocr.service;

import com.fabienIT.escaladefriendsp6ocr.controller.UserController;
import com.fabienIT.escaladefriendsp6ocr.model.Topo;
import com.fabienIT.escaladefriendsp6ocr.model.User;
import com.fabienIT.escaladefriendsp6ocr.repository.TopoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TopoService {

	@Autowired
	TopoRepository topoRepository;

	public void ajouter(Topo topo) {
		topoRepository.save(topo);
	}

	public List<Topo> findAllTopo (){return topoRepository.findAll(); }

	public Topo findTopoById(Long id) {
		return topoRepository.findTopoById(id);
	}

	public Topo findTopoByNom(String topo) {
		return topoRepository.findByNom(topo);
	}

	public Optional<Topo> findTopoSite(Long topo){return topoRepository.findById(topo);}


	/*public List <Topo> findAllTopo (){
		return topoRepository.findAll();
	}*/

	public Page<Topo> findAllNotId (Pageable pageable, @Param("model") Model model, @Param("user") User user, @Param("authentification") Authentication authentication, UserController userController){

		user = userController.userCo(model, authentication);

		return topoRepository.findAllNotId(pageable, user);
	}


	public Page<Topo> findByNameContains (@Param("keyword") String keyword, @Param("page") Pageable pageable) {
		if (keyword != null) {
			return topoRepository.findByNomContains(keyword, pageable);
		}
		System.out.println("Aucunes correspondances à votre recherche !!!");
		return topoRepository.findAll(pageable);
	}

	public void delete (Long id) {
		topoRepository.deleteById(id);
	}


	public void updateTopo(Topo topo) {
		//recuparation du topo en base via l'id
		Long id = topo.getId();
		Topo dbTopo = topoRepository.findById(id).get();
		//mise à jour (récupération) du nom depuis le formulaire d'edition
		dbTopo.setNom(topo.getNom());
		dbTopo.setRegion(topo.getRegion());
		dbTopo.setDate(topo.getDate());
		dbTopo.setDisponible(topo.isDisponible());
		dbTopo.setDescription(topo.getDescription());
		dbTopo.setReservation(topo.getReservation());
		//mise à jour dans la bdd (sauvegarde)
		topoRepository.save(dbTopo);
	}
}



