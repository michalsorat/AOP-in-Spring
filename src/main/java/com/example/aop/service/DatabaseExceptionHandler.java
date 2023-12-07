package com.example.aop.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DatabaseExceptionHandler {
    private static final int MAX_DATABASE_RECONNECTION_ATTEMPTS = 3;
    private static boolean databaseFixSuccessful = false;

    public static void handleDatabaseError() {
        int databaseReconnectionAttempts = 1;
        while (databaseReconnectionAttempts < MAX_DATABASE_RECONNECTION_ATTEMPTS) {
            if (retryDatabaseConnection(databaseReconnectionAttempts)) {
                return;
            } else {
                databaseReconnectionAttempts++;
            }
        }
        System.out.println("Unable to fix the database connection from aspect.");
    }

    private static boolean retryDatabaseConnection(int attemptNr) {
        System.out.println(attemptNr + ". retrying database connection...");

        // Simulate a successful database connection after retry
        databaseFixSuccessful = checkDatabaseConnectionPool();
        if (databaseFixSuccessful) {
            System.out.println("Auto-Remediation: Database connection reestablished.");
            return true;
        } else {
            System.out.println("Auto-Remediation: Database connection retry failed.");
            return false;
        }
    }

    public static boolean isDatabaseFixSuccessful() {
        return databaseFixSuccessful;
    }

    private static boolean checkDatabaseConnectionPool() {
        // Simulate checking a connection pool for available connections
        return new Random().nextBoolean();
    }
}
