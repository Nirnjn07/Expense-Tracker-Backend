
-- SQL script to create schema for ExpenseTracker
-- NOTE: application.properties is configured to use MySQL at jdbc:mysql://localhost:3306/expense_tracker_db
-- Run these statements in your MySQL server (or adapt to your DB) before starting the application.
DROP DATABASE IF EXISTS expense_tracker_db;
CREATE DATABASE IF NOT EXISTS expense_tracker_db;
USE expense_tracker_db;

-- Users table (matches com.ExpenseTracker.entity.User)
CREATE TABLE IF NOT EXISTS users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

-- Transactions table (matches com.ExpenseTracker.entity.Transaction)
CREATE TABLE IF NOT EXISTS transactions (
  transaction_id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(50),
  amount DOUBLE,
  transaction_date DATE,
  user_id INT,
  CONSTRAINT fk_transactions_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

insert into users (username, password) values ('alice', '$2a$12$7fAOHO0..mt1iUvy.Acucu47jbwJITT2XQUJlI9APmH//ONp3FJZu');
insert into users (username, password) values ('bob', '$2a$12$rhTIK0Y0GgCrOVgb4TIyDuao6L6Q9u53k9hHZI7Q4vZpYPTSH0fY6');
insert into users (username, password) values ('charlie', '$2a$12$9EpNSUppT//cZT8sJeo/GuOwp2MJ3bceFf73LoUuTpMXUZq1k1b82');

insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 15.50, '2024-06-01', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 200.00, '2024-06-02', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 500.00, '2024-06-03', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 200.00, '2024-06-04', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 400.00, '2024-06-05', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 200.00, '2024-06-06', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 100.00, '2024-06-07', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 39.00, '2024-06-08', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 48.00, '2024-06-09', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 75.00, '2024-06-10', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 100.00, '2024-06-11', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 75.00, '2024-06-12', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 100.00, '2024-06-13', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 50.00, '2024-06-14', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 48.00, '2024-06-15', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 50.00, '2024-06-16', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 90.00, '2024-06-17', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 80.00, '2024-06-18', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 22.50, '2024-06-19', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 400.00, '2024-06-20', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 65.00, '2024-06-21', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 15.00, '2024-06-22', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 150.00, '2024-06-23', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 80.00, '2024-06-24', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 600.00, '2024-06-25', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 45.99, '2024-06-26', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 120.50, '2024-06-27', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 250.00, '2024-06-28', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 55.00, '2024-06-29', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 18.00, '2024-06-30', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 1200.00, '2024-07-01', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 350.00, '2024-07-02', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 40.00, '2024-07-03', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 800.00, '2024-07-04', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 60.00, '2024-07-05', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 25.50, '2024-07-06', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 300.00, '2024-07-07', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 15.00, '2024-07-08', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 95.00, '2024-07-09', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 450.00, '2024-07-10', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 110.00, '2024-07-11', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 30.00, '2024-07-12', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 200.00, '2024-07-13', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 85.00, '2024-07-14', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 45.00, '2024-07-15', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 500.00, '2024-07-16', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 22.00, '2024-07-17', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 75.00, '2024-07-18', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 150.00, '2024-07-19', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 65.00, '2024-07-20', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 12.50, '2024-07-21', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 350.00, '2024-07-22', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 90.00, '2024-07-23', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 55.00, '2024-07-24', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 400.00, '2024-07-25', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 38.00, '2024-07-26', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 105.00, '2024-07-27', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 600.00, '2024-07-28', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 42.50, '2024-07-29', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 18.00, '2024-07-30', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 250.00, '2024-07-31', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 1200.00, '2024-08-01', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 400.00, '2024-08-02', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 35.00, '2024-08-03', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 800.00, '2024-08-04', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 50.00, '2024-08-05', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 20.00, '2024-08-06', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 300.00, '2024-08-07', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 12.00, '2024-08-08', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 88.00, '2024-08-09', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 450.00, '2024-08-10', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 115.00, '2024-08-11', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 28.00, '2024-08-12', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 200.00, '2024-08-13', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 70.00, '2024-08-14', 3);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 40.00, '2024-08-15', 1);
insert into transactions (type, amount, transaction_date, user_id) values ('Income', 500.00, '2024-08-16', 2);
insert into transactions (type, amount, transaction_date, user_id) values ('Expense', 25.00, '2024-08-17', 3);
