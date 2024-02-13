package com.fabienit.aeroclubpassion.repository;

import com.fabienit.aeroclubpassion.domain.UserRegistered;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserRegistered entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRegisteredRepository extends JpaRepository<UserRegistered, Long>, JpaSpecificationExecutor<UserRegistered> {
    @Query("select userRegistered from UserRegistered userRegistered where userRegistered.user.login = ?#{principal.username}")
    List<UserRegistered> findByUserIsCurrentUser();
}
