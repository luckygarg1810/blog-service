package com.luckygarg.blog_service.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luckygarg.blog_service.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	  Optional<User> findByUsername(String username);
	    boolean existsByUsername(String username);
	    boolean existsByEmail(String email);

	
}
