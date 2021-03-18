package com.macrosoftas.testHibernate.repository;

import com.macrosoftas.testHibernate.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoursRepository extends JpaRepository<Cours, String>{}
