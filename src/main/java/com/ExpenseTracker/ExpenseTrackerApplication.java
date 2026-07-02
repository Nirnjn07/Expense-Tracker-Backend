package com.ExpenseTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		// Ensure logs directory exists before Log4j2 initializes to avoid file appender errors
		try {
			java.nio.file.Path logs = java.nio.file.Paths.get("logs");
			java.nio.file.Files.createDirectories(logs);
		} catch (Exception ex) {
			// ignore - if directory cannot be created, Log4j2 may still try to create files depending on permissions
		}

		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

}
