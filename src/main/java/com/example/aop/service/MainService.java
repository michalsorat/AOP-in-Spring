package com.example.aop.service;

import com.example.aop.aspect.annotation.AdaptiveLogging;
import com.example.aop.aspect.annotation.AutomaticErrorHandling;
import com.example.aop.aspect.annotation.BehavioralAuthorization;
import com.example.aop.exception.DatabaseConnectionException;
import com.example.aop.exception.NetworkTimeoutException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MainService {

    @AdaptiveLogging
    public List<Integer> printPrimes(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("Invalid range: 'from' cannot be greater than 'to'");
        }
        long startTime = System.currentTimeMillis();
        List<Integer> primes = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Execution took [" + duration + "ms]");
        return primes;
    }

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number <= 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    @AutomaticErrorHandling
    public String simulateDatabaseConnectionException() {
        if (checkDatabaseConnectionPool()) {
            throw new DatabaseConnectionException("Unable to establish a database connection.");
        }
        return "Database function successfully passed. Performing a database operation...";
    }

    @AutomaticErrorHandling
    public String simulateNetworkTimeoutException() {
        if (checkNetworkConnection()) {
            throw new NetworkTimeoutException("Network timeout occurred.");
        }
        return "Network connection is working.";
    }

    private boolean checkDatabaseConnectionPool() {
        // Simulate checking a connection pool for available connections
        return new Random().nextBoolean();
    }

    private boolean checkNetworkConnection() {
        // Simulate checking a connection pool for available connections
        return new Random().nextBoolean();
    }

    @BehavioralAuthorization
    public String accessAdminInterface() {
        return "Access to admin interface granted. Welcome.";
    }
}
