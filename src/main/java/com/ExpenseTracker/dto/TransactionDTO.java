package com.ExpenseTracker.dto;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class TransactionDTO {
	@NotNull(message = "Transaction ID cannot be null")
	private Integer transactionId;
	@NotNull(message = "Transaction type cannot be null")
	private String type;
	private Double amount;
	private LocalDate transactionDate;
	@NotNull(message = "User cannot be null")
	@Valid
	private UserDTO user;
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, transactionDate, transactionId, type, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionDTO other = (TransactionDTO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(transactionDate, other.transactionDate)
				&& Objects.equals(transactionId, other.transactionId) && Objects.equals(type, other.type)
				&& Objects.equals(user, other.user);
	}
	@Override
	public String toString() {
		return "TransactionDTO [transactionId=" + transactionId + ", type=" + type + ", amount=" + amount
				+ ", transactionDate=" + transactionDate + ", user=" + user + "]";
	}
	
}
