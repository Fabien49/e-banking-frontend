
package com.fabienIT.friendsEscalade.repository;


import com.fabienIT.friendsEscalade.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

