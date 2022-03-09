package com.fabienit.biblioapi.repository;

import com.fabienit.biblioapi.model.Usager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsagerRepository extends JpaRepository <Usager, Integer>{

    Usager findById(int id);
    Usager findUsagerByEmail(String email);


}
