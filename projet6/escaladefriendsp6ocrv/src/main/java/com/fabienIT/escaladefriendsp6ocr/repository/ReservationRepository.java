
package com.fabienIT.escaladefriendsp6ocr.repository;


import com.fabienIT.escaladefriendsp6ocr.model.Reservation;
import com.fabienIT.escaladefriendsp6ocr.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {



    public List<Reservation> findByUserId(int id);

    public List<Reservation> findByTopoId(Long id);

/*   Reservation findAllById(Long id);
    //Topo  deleteById();

   Reservation findByNom(String nom);*/

//@Query("SELECT r FROM Reservation r WHERE CONCAT (r.nom, ' ',r.proprietaire, ' ',r.region, ' ',r.nbSites, ' ',r.nbVoies, ' ',r.cotationMin, ' ',r.cotationMax, ' ') LIKE %?1%")
/*   public Page<Reservation> findByNomContains(String keyword, Pageable pageable);*/



/*    @Query("SELECT r FROM Reservation r WHERE r.user NOT LIKE :user ")
    public  Page<Reservation> findAllNotId(Pageable pageable,  @Param("user") User user);*/


/*user = userController.userCo(model, authentication);
        email = user.getEmail();





    public Optional <Reservation> findById (Long id);

    Reservation findReservationById (Long id);*/

//    Topo findByIdNotIn ();





}

