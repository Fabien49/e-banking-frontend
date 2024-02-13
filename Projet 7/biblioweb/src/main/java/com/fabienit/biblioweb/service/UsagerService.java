package com.fabienit.biblioweb.service;


import com.fabienit.biblioweb.model.Usager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public interface UsagerService {

	RestTemplate restTemplate = new RestTemplate();


	public default ResponseEntity<Usager> saveUsager(Usager usager, RestTemplate restTemplate){
		return restTemplate.postForEntity("http://localhost:8081/biblio-api/api/auth/signup", usager, Usager.class);
	}

	public default ResponseEntity<Usager> usagerById(@RequestParam(name = "id") int id) {
		return restTemplate.getForEntity("http://localhost:8080/api/usagers/" + id, Usager.class);
	}

	public default Usager listUsager(Usager usager) {
		ResponseEntity<Usager[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/usagers/liste_usagers", Usager[].class);
		Usager[] usagersArray = responseEntity.getBody();
		Arrays.stream(usagersArray).map(usager::getName).collect(Collectors.toList());
		List<Usager> listeUsager = new ArrayList<Usager>(Arrays.asList(usagersArray));
		return (Usager) listeUsager;

	}


/*	public ResponseEntity<JwtResponse> login(Usager userAccount) {
		HttpEntity<Usager> entity = new HttpEntity<>(userAccount);
		try {
			restTemplate.exchange("http://localhost:8081/biblio-api/api/auth/signin", HttpMethod.POST, entity, Object.class);
		} catch (HttpClientErrorException e) {
			e.getResponseBodyAsString();
		}
		return restTemplate.exchange("http://localhost:8081/biblio-api/api/auth/signin", HttpMethod.POST, entity, JwtResponse.class);
	}*/

}
