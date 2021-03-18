package com.macrosoftas.testHibernate.repository;

import com.macrosoftas.testHibernate.model.Profs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface ProfsRepository extends JpaRepository<Profs, Long> {
	 Profs findByEmail(String email);
	 
	 List<Profs> findByName(String name);


}
