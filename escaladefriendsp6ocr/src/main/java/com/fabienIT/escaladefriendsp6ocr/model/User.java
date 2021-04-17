package com.fabienIT.escaladefriendsp6ocr.model;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	@Column(name = "email", unique=true)
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	@Transient
	private String password;
	@Column(name = "name")
	@NotEmpty(message = "*Please provide your name")
	private String name;
	@Column(name = "last_name")
	@NotEmpty(message = "*Please provide your last name")
	private String lastName;
	/*@Column(name = "sexe")
	@NotEmpty(message = "*Please provide your sexe")*/
	private String sexe;
	@Column(name = "voie")
	@NotEmpty(message = "*Please provide your voie")
	private String voie;
	@Column(name = "codePostal")
	@NotEmpty(message = "*Please provide your code postal")
	private String codePostal;
	@Column(name = "commune")
	@NotEmpty(message = "*Please provide your commune")
	private String commune;
	@Column(name = "niveau")
	private String niveau;
	@Column(name = "active")
	private boolean active;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
	private Set<Topo> topo;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
	private Set<Reservation> reservation;

	@OneToMany(mappedBy = "site", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
	private Set<Commentaire> commentaire;

	public User() {
	}


	public User(int id, @Email(message = "*Please provide a valid Email") @NotEmpty(message = "*Please provide an email") String email, @Length(min = 5, message = "*Your password must have at least 5 characters") @NotEmpty(message = "*Please provide your password") String password, @NotEmpty(message = "*Please provide your name") String name, @NotEmpty(message = "*Please provide your last name") String lastName, @NotEmpty(message = "*Please provide your voie") String voie, @NotEmpty(message = "*Please provide your code postal") String codePostal, @NotEmpty(message = "*Please provide your commune") String commune, String niveau, boolean active, Set<Role> roles, Set<Topo> topo, Set<Reservation> reservation, Set<Commentaire> commentaire) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.voie = voie;
		this.codePostal = codePostal;
		this.commune = commune;
		this.niveau = niveau;
		this.active = active;
		this.roles = roles;
		this.topo = topo;
		this.commentaire = commentaire;

	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public Set<Topo> getTopo() {return topo;}

	public void setTopo(Set<Topo> topo) {this.topo = topo;}

	public Set<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(Set<Reservation> reservation) {
		this.reservation = reservation;
	}

	public Set<Commentaire> getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(Set<Commentaire> commentaire) {
		this.commentaire = commentaire;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return equals(user.id);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", sexe='" + sexe + '\'' +
				", voie='" + voie + '\'' +
				", codePostal='" + codePostal + '\'' +
				", commune='" + commune + '\'' +
				", niveau='" + niveau + '\'' +
				", active=" + active +
				", roles=" + roles +
				", topo=" + topo +
				", reservation=" + reservation +
				", commentaire=" + commentaire +
				'}';
	}
}
