package com.fabienIT.friendsEscalade.repository;


import com.fabienIT.friendsEscalade.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {


	User findByEmail(String email);

	User findAllById(Long id);

	 User findUserById(Long id);

	public Page<User> findByNameContains(String keyword, Pageable pageable);
}
