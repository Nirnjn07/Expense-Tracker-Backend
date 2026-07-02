package com.ExpenseTracker.service;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.exception.ExpenseTrackerException;
public interface ExpenseTrackerService {
 public List<TransactionDTO> getTransactionsByUserId(Integer userId, Pageable pageable) throws ExpenseTrackerException;
 public Integer addTransaction(TransactionDTO transactionDTO) throws ExpenseTrackerException;
 // Updated signatures: include ownerUserId so service enforces ownership checks
 public void updateTransaction(Integer transactionId, TransactionDTO transactionDTO, Integer ownerUserId) throws ExpenseTrackerException;
 public void deleteTransaction(Integer transactionId, Integer ownerUserId) throws ExpenseTrackerException;
 public Double getTotalExpensesByUserId(Integer userId) throws ExpenseTrackerException;
}
