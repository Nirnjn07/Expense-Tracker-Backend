package com.ExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.dto.UserDTO;
import com.ExpenseTracker.entity.User;
import com.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.exception.ExpenseTrackerException;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {
  @Autowired	
  Environment env;
  @Autowired
  com.ExpenseTracker.service.ExpenseTrackerService expenseTrackerService;
  @Autowired
  UserRepository userRepository;
  @PostMapping("/addTransaction")
  // we do not validate the incoming user field: the server will assign the authenticated user
  public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO transactionDTO) throws ExpenseTrackerException {
    // derive authenticated user
    String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new ExpenseTrackerException("Service.USER_NOT_FOUND"));
    // ensure the transactionDTO's user is set to the authenticated user
    UserDTO u = new UserDTO();
    u.setUserId(user.getUserId());
    u.setUsername(user.getUsername());
    transactionDTO.setUser(u);

    Integer transactionId = expenseTrackerService.addTransaction(transactionDTO);
    String successMessage = env.getProperty("API.TRANSACTION_ADD_SUCCESS") + transactionId;
    return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
  }
  @GetMapping("/getTotalExpenses")
  public ResponseEntity<Double> getTotalExpenses() throws ExpenseTrackerException {
    String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new ExpenseTrackerException("Service.USER_NOT_FOUND"));
    Double totalExpenses = expenseTrackerService.getTotalExpensesByUserId(user.getUserId());
    return new ResponseEntity<>(totalExpenses, HttpStatus.OK);
  }
  @GetMapping("/getTransactions")
  public ResponseEntity<java.util.List<TransactionDTO>> getTransactions(@PageableDefault(size=10, sort="transactionDate", direction=Sort.Direction.DESC) Pageable pageable) throws ExpenseTrackerException {
    String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new ExpenseTrackerException("Service.USER_NOT_FOUND"));
    java.util.List<TransactionDTO> transactions = expenseTrackerService.getTransactionsByUserId(user.getUserId(), pageable);
    return new ResponseEntity<>(transactions, HttpStatus.OK);
  }
  @PutMapping("/updateTransaction/{transactionId}")
  public ResponseEntity<String> updateTransaction(@PathVariable Integer transactionId, @Valid @RequestBody TransactionDTO transactionDTO) throws ExpenseTrackerException {
    String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new ExpenseTrackerException("Service.USER_NOT_FOUND"));
    expenseTrackerService.updateTransaction(transactionId, transactionDTO, user.getUserId());
    String successMessage = env.getProperty("API.TRANSACTION_UPDATE_SUCCESS");
    return new ResponseEntity<>(successMessage, HttpStatus.OK);
  }
  @DeleteMapping("/deleteTransaction/{transactionId}")
  public ResponseEntity<String> deleteTransaction(@PathVariable Integer transactionId) throws ExpenseTrackerException {
    String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new ExpenseTrackerException("Service.USER_NOT_FOUND"));
    expenseTrackerService.deleteTransaction(transactionId, user.getUserId());
    String successMessage = env.getProperty("API.TRANSACTION_DELETE_SUCCESS");
    return new ResponseEntity<>(successMessage, HttpStatus.OK);
  }
}
