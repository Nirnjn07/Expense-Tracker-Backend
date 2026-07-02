package com.ExpenseTracker.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ExpenseTracker.entity.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	// Return transactions for a user with an explicit Sort parameter.
	// Method name should be findByUserId when using a Sort parameter.
	Page<Transaction> findByUserUserId(Integer userId, Pageable pageable);

	List<Transaction> findByUserUserId(Integer userId);
}
