package com.fabienit.biblioapi.service;


import com.fabienit.biblioapi.model.Usager;

public interface UsagerService {

	Usager findUsagerByEmail(String email);
	void saveUsager(Usager usager);

}
