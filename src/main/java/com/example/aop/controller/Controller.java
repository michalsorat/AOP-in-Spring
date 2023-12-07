package com.example.aop.controller;

import com.example.aop.exception.AuthorizationException;
import com.example.aop.exception.DatabaseConnectionException;
import com.example.aop.exception.NetworkTimeoutException;
import com.example.aop.service.DatabaseExceptionHandler;
import com.example.aop.service.MainService;
import com.example.aop.service.NetworkExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    MainService mainService;

    @GetMapping("/print/primes")
    public List<Integer> printPrimes(@RequestParam int from, @RequestParam int to) {
        return mainService.printPrimes(from, to);
    }

    @GetMapping("/simulateDatabaseException")
    public String simulateDatabaseException() {
        try {
            return mainService.simulateDatabaseConnectionException();
        } catch (DatabaseConnectionException e) {
            if (DatabaseExceptionHandler.isDatabaseFixSuccessful()) {
                return "Simulated DatabaseConnectionException handled and fixed. Performing a database operation...";
            } else {
                return "Simulated DatabaseConnectionException handled, but the fix failed.";
            }
        }
    }

    @GetMapping("/simulateNetworkException")
    public String simulateNetworkException() {
        try {
            return mainService.simulateNetworkTimeoutException();
        } catch (NetworkTimeoutException e) {
            if (NetworkExceptionHandler.isNetworkFixSuccessful()) {
                return "Simulated NetworkTimeoutException handled and fixed.";
            } else {
                return "Simulated NetworkTimeoutException handled, but the fix failed.";
            }
        }
    }

    @GetMapping("/admin")
    public String accessAdminInterface() {
        try {
            return mainService.accessAdminInterface();
        } catch (AuthorizationException e) {
            return e.getMessage();
        }
    }
}
