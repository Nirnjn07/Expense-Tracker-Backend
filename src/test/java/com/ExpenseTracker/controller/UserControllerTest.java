package com.ExpenseTracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.dto.UserDTO;
import com.ExpenseTracker.entity.User;
import com.ExpenseTracker.exception.ExpenseTrackerException;
import com.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.security.JwtUtil;
import com.ExpenseTracker.service.ExpenseTrackerService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseTrackerService expenseTrackerService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser(username = "alice")
    void addTransaction_shouldReturnCreatedMessage() throws Exception {

        User user = new User();
        user.setUserId(1);
        user.setUsername("alice");

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(expenseTrackerService.addTransaction(any(TransactionDTO.class))).thenReturn(20);

        mockMvc.perform(post("/users/addTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"transactionId\":null,\"type\":\"expense\",\"amount\":150.5,\"transactionDate\":\"2026-09-03\",\"user\":{\"userId\":1,\"username\":\"alice\"}}"))
            .andExpect(status().isCreated())
            .andExpect(content().string("Transaction successfully added with ID:20"));
    }

    @Test
    @WithMockUser(username = "alice")
    void getTotalExpenses_shouldReturnCalculatedTotal() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("alice");

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(expenseTrackerService.getTotalExpensesByUserId(1)).thenReturn(200.75);

        mockMvc.perform(get("/users/getTotalExpenses"))
            .andExpect(status().isOk())
            .andExpect(content().string("200.75"));
    }

    @Test
    @WithMockUser(username = "alice")
    void getTransactions_shouldReturnTransactionList() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("alice");

        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(2);
        dto.setType("income");
        dto.setAmount(90.0);
        dto.setTransactionDate(LocalDate.of(2026, 9, 3));
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setUsername("alice");
        dto.setUser(userDTO);

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(expenseTrackerService.getTransactionsByUserId(eq(1), any(Pageable.class))).thenReturn(List.of(dto));

        mockMvc.perform(get("/users/getTransactions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].type").value("income"))
            .andExpect(jsonPath("$[0].amount").value(90.0));
    }

    @Test
    @WithMockUser(username = "alice")
    void updateTransaction_shouldReturnOk() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("alice");

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        doNothing().when(expenseTrackerService).updateTransaction(eq(5), any(TransactionDTO.class), eq(1));

        mockMvc.perform(put("/users/updateTransaction/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"transactionId\":5,\"type\":\"expense\",\"amount\":75.0,\"transactionDate\":\"2026-09-03\",\"user\":{\"userId\":1,\"username\":\"alice\"}}"))
            .andExpect(status().isOk())
            .andExpect(content().string("Transaction successfully updated with ID:"));
    }

    @Test
    @WithMockUser(username = "alice")
    void deleteTransaction_shouldReturnOk() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("alice");

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        doNothing().when(expenseTrackerService).deleteTransaction(7, 1);

        mockMvc.perform(delete("/users/deleteTransaction/7"))
            .andExpect(status().isOk())
            .andExpect(content().string("Transaction successfully deleted with ID:"));
    }

    @Test
    @WithMockUser(username = "alice")
    void userController_shouldHandleServiceException() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("alice");

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(expenseTrackerService.getTotalExpensesByUserId(1)).thenThrow(new ExpenseTrackerException("Service.NO_TRANSACTION_FOUND"));

        mockMvc.perform(get("/users/getTotalExpenses"))
            .andExpect(status().isBadRequest());
    }
}