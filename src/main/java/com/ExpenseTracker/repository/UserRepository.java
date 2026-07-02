package com.ExpenseTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ExpenseTracker.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	java.util.Optional<User> findByUsername(String username);

}
