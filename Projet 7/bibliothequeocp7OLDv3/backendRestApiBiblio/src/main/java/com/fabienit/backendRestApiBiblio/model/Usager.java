package com.fabienit.backendRestApiBiblio.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usager")
public class Usager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usager_id")
    private int id;
    @Column(name = "mail", unique=true)
    @Email(message = "*Entrez un mail valide s'il vous plait")
    @NotEmpty(message = "*Entrez votre mail s'il vous plait")
    private String email;
    @Column(name = "mot_de_passe")
    @Length(min = 5,max = 20, message = "*Votre mot de passe doit être compris entre 5 et 20 caratères")
    @NotEmpty(message = "*Entrez votre mot de passe s'il vous plait")
    @Transient
    private String password;
    @Column(name = "nom")
    @NotEmpty(message = "*Entrez votre nom s'il vous plait")
    private String name;
    @Column(name = "prenom")
    @NotEmpty(message = "*Entrez votre prénom s'il vous plait")
    private String lastName;
    @Column(name = "voie")
    @NotEmpty(message = "*Entrez votre voie s'il vous plait")
    private String voie;
    @Column(name = "codePostal")
    @NotEmpty(message = "*Entrez votre code postal s'il vous plait")
    private String codePostal;
    @Column(name = "commune")
    @NotEmpty(message = "*Entrez votre commune s'il vous plait")
    private String commune;
    @Column(name = "active")
    private boolean active;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usager_role", joinColumns = @JoinColumn(name = "usager_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public Usager() {
    }


    public Usager(int id, @Email(message = "*Entrez un mail valide s'il vous plait") @NotEmpty(message = "*Please provide an email") String email, @Length(min = 5, message = "*Votre mot de passe doit être compris entre 5 et 20 caratères") @NotEmpty(message = "*Entrez votre mot de passe s'il vous plait") String password, @NotEmpty(message = "*Please provide your name") String name, @NotEmpty(message = "*Please provide your last name") String lastName, @NotEmpty(message = "*Please provide your voie") String voie, @NotEmpty(message = "*Please provide your code postal") String codePostal, @NotEmpty(message = "*Please provide your commune") String commune, String niveau, boolean active, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.voie = voie;
        this.codePostal = codePostal;
        this.commune = commune;
        this.active = active;
        this.roles = roles;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usager usager = (Usager)o;
        return equals(usager.id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", voie='" + voie + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", commune='" + commune + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
