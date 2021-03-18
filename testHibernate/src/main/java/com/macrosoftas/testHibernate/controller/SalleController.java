package com.macrosoftas.testHibernate.controller;

import java.io.Serializable;

public class SalleController implements Serializable{
	
	private String  titre;
	
	private String[] tabGenres;
	
	
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String[] getTabGenres() {
		return tabGenres;
	}

	public void setTabGenres(String[] tabGenres) {
		this.tabGenres = tabGenres;
	}


	
	

}
