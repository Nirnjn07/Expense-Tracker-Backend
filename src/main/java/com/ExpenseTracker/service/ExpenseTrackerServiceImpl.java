package com.ExpenseTracker.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.entity.Transaction;
import com.ExpenseTracker.exception.ExpenseTrackerException;
import com.ExpenseTracker.repository.TransactionRepository;
import com.ExpenseTracker.repository.UserRepository;
@org.springframework.stereotype.Service
@org.springframework.transaction.annotation.Transactional
public class ExpenseTrackerServiceImpl implements ExpenseTrackerService {
	@Autowired UserRepository userRepository;
	@Autowired TransactionRepository transactionRepository;
	ModelMapper modelMapper = new ModelMapper();
	@Override
	public List<TransactionDTO> getTransactionsByUserId(Integer userId, Pageable pageable)
			throws ExpenseTrackerException {
		// TODO Auto-generated method stub
		Page<Transaction> transactions = transactionRepository.findByUserUserId(userId, pageable);
		if(transactions.isEmpty()) {
			throw new ExpenseTrackerException("Service.NO_TRANSACTIONS_FOUND"+ userId);
		}
		return transactions.stream()
				.map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
				.toList();
	}

	@Override
	public Integer addTransaction(TransactionDTO transactionDTO) throws ExpenseTrackerException {
		// TODO Auto-generated method stub
		 Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
	        transaction = transactionRepository.save(transaction);
	        return transaction.getTransactionId();
		
		
	}

	@Override
	public void updateTransaction(Integer transactionId, TransactionDTO transactionDTO, Integer ownerUserId)
			throws ExpenseTrackerException {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
		if (!optionalTransaction.isPresent()) {
			throw new ExpenseTrackerException("Service.NO_TRANSACTION_FOR_ID" + transactionId);
		}
		Transaction transaction = optionalTransaction.get();
		if (transaction.getUser() == null || !transaction.getUser().getUserId().equals(ownerUserId)) {
			throw new ExpenseTrackerException("Service.UNAUTHORIZED_OPERATION");
		}
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setType(transactionDTO.getType());
		transaction.setTransactionDate(transactionDTO.getTransactionDate());
		transactionRepository.save(transaction);
	}

	@Override
	public void deleteTransaction(Integer transactionId, Integer ownerUserId) throws ExpenseTrackerException {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
		if (!optionalTransaction.isPresent()) {
			throw new ExpenseTrackerException("Service.NO_TRANSACTION_FOR_ID" + transactionId);
		}
		Transaction transaction = optionalTransaction.get();
		if (transaction.getUser() == null || !transaction.getUser().getUserId().equals(ownerUserId)) {
			throw new ExpenseTrackerException("Service.UNAUTHORIZED_OPERATION");
		}
		transactionRepository.deleteById(transactionId);
	}

	@Override
	public Double getTotalExpensesByUserId(Integer userId) throws ExpenseTrackerException {
		// TODO Auto-generated method stub
		List<Transaction> transactions = transactionRepository.findByUserUserId(userId);
		if (transactions.isEmpty()) {
			throw new ExpenseTrackerException("Service.NO_TRANSACTION_FOR_ID" + userId);
		}
		Double totalexpense=0.0;
		for (Transaction transaction : transactions) {
			if (transaction.getType().equalsIgnoreCase("expense")) {
				totalexpense += transaction.getAmount();
			}else if (transaction.getType().equalsIgnoreCase("income")) {
				totalexpense -= transaction.getAmount();
			}
		}
		return totalexpense;
		
	}
	// Implement the methods defined in the ExpenseTrackerService interface

}
