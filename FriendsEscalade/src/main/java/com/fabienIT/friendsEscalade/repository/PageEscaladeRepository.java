package com.fabienIT.friendsEscalade.repository;


import com.fabienIT.friendsEscalade.model.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageEscaladeRepository extends JpaRepository<Site, Long> {

    public Page<Site> findByNomContains(String mc, Pageable pageable);




}