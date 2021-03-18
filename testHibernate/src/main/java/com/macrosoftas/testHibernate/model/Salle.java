package com.macrosoftas.testHibernate.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity
public class Salle implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "salle_id")
    private Long id;
	
	private String numero;
	private String batiment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBatiment() {
		return batiment;
	}

	public void setBatiment(String batiment) {
		this.batiment = batiment;
	}


	@Override
	public String toString() {
		return "Salle{" +
				"id=" + id +
				", numero='" + numero + '\'' +
				", batiment='" + batiment + '\'' +
				", eleve=" + eleve +
				'}';
	}

	@OneToMany(mappedBy="salle")
	private Set<Eleve> eleve;
}
