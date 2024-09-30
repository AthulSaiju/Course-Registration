	package com.project.scope.repository;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	
	import com.project.scope.model.User;
	
	
	
	public interface UserRepo extends JpaRepository<User,Long>{
		User findByToken(String token);
		User findByEmail(String email);
	
	}
