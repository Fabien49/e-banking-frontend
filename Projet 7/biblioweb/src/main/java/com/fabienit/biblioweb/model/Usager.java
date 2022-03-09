package com.fabienit.biblioweb.biblioweb.model;


public class Usager {


	private int id;
	private String email;
	private String password;
	private String name;
	private String lastName;
	private String voie;
	private String codePostal;
	private String commune;
	private boolean active;



	public Usager() {
	}


	public Usager(int id, String email, String password, String name, String lastName, String voie, String codePostal, String commune, boolean active) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.voie = voie;
		this.codePostal = codePostal;
		this.commune = commune;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getVoie() {
		return voie;
	}

	public void setVoie(String voie) {
		this.voie = voie;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usager usager = (Usager)o;
		return equals(usager.id);
	}

	@Override
	public String toString() {
		return "Usager{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", voie='" + voie + '\'' +
				", codePostal='" + codePostal + '\'' +
				", commune='" + commune + '\'' +
				", active=" + active +
				'}';
	}
}
