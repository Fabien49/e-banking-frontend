package com.fabienIT.escaladefriendsp6ocr;

import com.fabienIT.escaladefriendsp6ocr.model.*;
import com.fabienIT.escaladefriendsp6ocr.repository.TopoRepository;
import com.fabienIT.escaladefriendsp6ocr.repository.UserRepository;
import com.fabienIT.escaladefriendsp6ocr.service.SiteService;
import com.fabienIT.escaladefriendsp6ocr.service.TopoService;
import com.fabienIT.escaladefriendsp6ocr.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class EscaladeApplication implements CommandLineRunner{

	@Value("${chemin.site.image}")
	private String siteImageRoot;


	
	private static final Logger logger = LoggerFactory.getLogger(EscaladeApplication.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	TopoService topoService;

	@Autowired
	TopoRepository topoRepository;

	@Autowired
	SiteService siteService;
	
	/*@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;*/

	public static void main(String[] args) {
		SpringApplication.run(EscaladeApplication.class, args);
		logger.info("Start Les Amis de l'Escalade Application ...");
	}

	public void run(String... args) throws Exception {
		logger.info("Le chemin des images des sites est : " +siteImageRoot);

		testReservationTopo();
		ajouterTopo();
		ajouterSite();
		//afficherTopo();
		//saveAdmin();
		//saveMembre();


		//createUser();
	}

/*	public void afficherTopo(){
			List<Topo> topoList = topoService.findAllTopo();

		System.out.println("+" +
				"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++La liste des topos est : *******+++++++++++++++++********************" + topoList);


	}*/

/*	public void saveAdmin() {
		HashSet<Role> roles = null;
		User user = new User();
		user.setEmail("admin@admin.com");
		user.setPassword("admin");
		user.setName("ad");
		user.setLastName("min");
		user.setSexe("Homme");
		user.setVoie("42 rue de l'admin");
		user.setCodePostal("75000");
		user.setCommune("Paris");
		user.setTopo(null);
		user.setCommentaire(null);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("ADMIN");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		System.out.println("L'admin enregistré est : " + user);
	}*/

/*
		public void saveMembre() {
		HashSet<Role> roles = null;
		User user = new User();
		user.setEmail("membre@membre.com");
		BCryptPasswordEncoder bCryptPasswordEncoderLocal = new BCryptPasswordEncoder();
		String encodepwd = bCryptPasswordEncoderLocal.encode("membre");
		user.setPassword(encodepwd);
		//user.setPassword("membre");
		user.setName("mem");
		user.setLastName("bre");
		user.setSexe("Homme");
		user.setVoie("15 rue du membre");
		user.setCodePostal("75000");
		user.setCommune("Paris");
		user.setTopo(null);
		user.setCommentaire(null);
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("MEMBRE");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		System.out.println("Le membre enregistré est : " + user);
	}
*/




	private void testReservationTopo(){
		System.out.println("je suis en train de tester testReservationTopo");
	}

	private void ajouterTopo (){

	  /*  topoService.ajouter(new Topo(null, "Les Alpes", "Jean Philippe","Alpes", 18, 156, "4a", "9b+", false, false, false));
		topoService.ajouter(new Topo(null, "Les 1000 Vaches", "Stéphanie","Centre", 7, 39, "4a", "7c+", false, false, false));
		topoService.ajouter(new Topo(null, "La Pente", "Clarisse","Bretagne", 10, 86, "4a", "8b", false, false, false));
		topoService.ajouter(new Topo(null, "Les Pyrénées", "Fabien","Occitanie", 19, 147, "4a", "9b+", false, false, false));
		topoService.ajouter(new Topo(null, "L'île de beauté", "Fabrice","Corse", 9, 52, "4a", "9b+", false, false, false));*/

	}



	public void ajouterSite() {
	/*	Site site = (new Site(null, "Le Peyssin", "Alpes",125, "4a", "9b+", "Super Site", "LePeyssin.jpg"));
		siteService.ajouter(site);
		System.out.println("Le site ajouté est : " + site);
		Topo topo = (new Topo(null, "Les Alpes", "Jean Philippe","Alpes", 18, 156, "4a", "9b+", false, false, false, site));
		topoService.ajouter(topo);
		System.out.println("le topo ajouté est: " + topo);
		topo = (new Topo(null, "La folie", "Souleymane","Alpes", 15, 97, "4a", "9a", false, false, false, site));
		topoService.ajouter(topo);
		System.out.println("le topo ajouté est: " + topo);
		site = (new Site(null, "BalmeYenne", "Alpes",115, "4a", "9b+", "Super Site", "BalmeYenne.jpg"));
		siteService.ajouter(site);
		System.out.println("Le site ajouté est : " + site);
		topo = (new Topo(null, "les 1000 vaches", "Sophie","Centre", 12, 86, "4a", "8a+", false, false, false, site));
		topoService.ajouter(topo);
		System.out.println("le topo ajouté est: " + topo);
*/
	}

	/*private void createUser(){
		System.out.println("je suis en train de tester createUser");
		List<String> userdata = Arrays.asList("ADMIN", "ADMIN", "admin@test.com","admin2017");
        logger.debug("**** Create default user  {}", userdata);

		User user = new User();
		user.setName("ADMIN");
		user.setLastName("ADMIN");
		user.setEmail("admin@test.com");
		//BCryptPasswordEncoder bCryptPasswordEncoderLocal = new BCryptPasswordEncoder();
		//String encodepwd = bCryptPasswordEncoderLocal.encode("admin2017");
		//user.setPassword(encodepwd);
		//System.out.println("admin2017  encoder = " +encodepwd);
		user.setPassword("$2a$10$fE7BKQcc.tesDzaptjL8luXZB6MV5rvUJ13ub5aVYKqnoPmMqYd8m");
		user.setActive(true);
		//Role
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("ADMIN");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
	}*/

}
