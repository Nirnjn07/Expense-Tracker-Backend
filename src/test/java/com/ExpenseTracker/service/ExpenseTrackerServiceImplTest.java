package com.ExpenseTracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.dto.UserDTO;
import com.ExpenseTracker.entity.Transaction;
import com.ExpenseTracker.entity.User;
import com.ExpenseTracker.exception.ExpenseTrackerException;
import com.ExpenseTracker.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class ExpenseTrackerServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ExpenseTrackerServiceImpl expenseTrackerService;

    @Test
    void getTransactionsByUserId_whenTransactionsExist_returnsMappedDtos() throws ExpenseTrackerException {
        User user = new User();
        user.setUserId(1);
        user.setUsername("alice");

        Transaction transaction = new Transaction();
        transaction.setTransactionId(10);
        transaction.setType("expense");
        transaction.setAmount(125.5);
        transaction.setTransactionDate(LocalDate.of(2026, 9, 3));
        transaction.setUser(user);

        Page<Transaction> page = new PageImpl<>(List.of(transaction));
        when(transactionRepository.findByUserUserId(eq(1), any(Pageable.class))).thenReturn(page);

        List<TransactionDTO> result = expenseTrackerService.getTransactionsByUserId(1, PageRequest.of(0, 10));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo("expense");
        assertThat(result.get(0).getAmount()).isEqualTo(125.5);
        assertThat(result.get(0).getUser().getUserId()).isEqualTo(1);
    }

    @Test
    void getTransactionsByUserId_whenNoTransactions_throwsException() {
        when(transactionRepository.findByUserUserId(eq(1), any(Pageable.class))).thenReturn(Page.empty());

        assertThatThrownBy(() -> expenseTrackerService.getTransactionsByUserId(1, PageRequest.of(0, 10)))
            .isInstanceOf(ExpenseTrackerException.class)
            .hasMessageContaining("Service.NO_TRANSACTIONS_FOUND");
    }

    @Test
    void addTransaction_shouldPersistAndReturnGeneratedId() throws ExpenseTrackerException {
        TransactionDTO dto = new TransactionDTO();
        dto.setType("expense");
        dto.setAmount(75.0);
        dto.setTransactionDate(LocalDate.of(2026, 9, 3));

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(2);
        userDTO.setUsername("bob");
        dto.setUser(userDTO);

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction persisted = invocation.getArgument(0);
            persisted.setTransactionId(101);
            return persisted;
        });

        Integer savedId = expenseTrackerService.addTransaction(dto);

        assertThat(savedId).isEqualTo(101);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void updateTransaction_whenAuthorized_updatesFields() throws ExpenseTrackerException {
        User user = new User();
        user.setUserId(5);
        user.setUsername("charlie");

        Transaction existing = new Transaction();
        existing.setTransactionId(8);
        existing.setType("expense");
        existing.setAmount(10.0);
        existing.setTransactionDate(LocalDate.of(2026, 1, 1));
        existing.setUser(user);

        TransactionDTO dto = new TransactionDTO();
        dto.setType("income");
        dto.setAmount(200.0);
        dto.setTransactionDate(LocalDate.of(2026, 2, 5));

        when(transactionRepository.findById(8)).thenReturn(Optional.of(existing));

        expenseTrackerService.updateTransaction(8, dto, 5);

        assertThat(existing.getType()).isEqualTo("income");
        assertThat(existing.getAmount()).isEqualTo(200.0);
        assertThat(existing.getTransactionDate()).isEqualTo(LocalDate.of(2026, 2, 5));
        verify(transactionRepository).save(existing);
    }

    @Test
    void updateTransaction_whenUnauthorized_throwsException() {
        User user = new User();
        user.setUserId(5);

        Transaction existing = new Transaction();
        existing.setTransactionId(8);
        existing.setType("expense");
        existing.setAmount(10.0);
        existing.setUser(user);

        when(transactionRepository.findById(8)).thenReturn(Optional.of(existing));

        TransactionDTO dto = new TransactionDTO();
        dto.setType("income");
        dto.setAmount(200.0);

        assertThatThrownBy(() -> expenseTrackerService.updateTransaction(8, dto, 9))
            .isInstanceOf(ExpenseTrackerException.class)
            .hasMessageContaining("Service.UNAUTHORIZED_OPERATION");
    }

    @Test
    void deleteTransaction_whenAuthorized_deletesRecord() throws ExpenseTrackerException {
        User user = new User();
        user.setUserId(7);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(22);
        transaction.setUser(user);

        when(transactionRepository.findById(22)).thenReturn(Optional.of(transaction));

        expenseTrackerService.deleteTransaction(22, 7);

        verify(transactionRepository).deleteById(22);
    }

    @Test
    void getTotalExpensesByUserId_returnsNetAmount() throws ExpenseTrackerException {
        when(transactionRepository.findByUserUserId(1)).thenReturn(List.of(
            expense(100.0),
            income(40.0),
            expense(25.0)
        ));

        Double total = expenseTrackerService.getTotalExpensesByUserId(1);

        assertThat(total).isEqualTo(85.0);
    }

    @Test
    void getTotalExpensesByUserId_whenNoTransactions_throwsException() {
        when(transactionRepository.findByUserUserId(2)).thenReturn(List.of());

        assertThatThrownBy(() -> expenseTrackerService.getTotalExpensesByUserId(2))
            .isInstanceOf(ExpenseTrackerException.class)
            .hasMessageContaining("Service.NO_TRANSACTION_FOR_ID");
    }

    private Transaction expense(Double amount) {
        Transaction transaction = new Transaction();
        transaction.setType("expense");
        transaction.setAmount(amount);
        return transaction;
    }

    private Transaction income(Double amount) {
        Transaction transaction = new Transaction();
        transaction.setType("income");
        transaction.setAmount(amount);
        return transaction;
    }
}
