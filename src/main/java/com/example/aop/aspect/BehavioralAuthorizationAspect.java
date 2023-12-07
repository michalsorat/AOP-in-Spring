package com.example.aop.aspect;

import com.example.aop.Enum.Role;
import com.example.aop.exception.AuthorizationException;
import com.example.aop.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BehavioralAuthorizationAspect {

    @Before("@annotation(com.example.aop.aspect.annotation.BehavioralAuthorization)")
    public void checkBehavioralAuthorization(JoinPoint joinPoint) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("BEHAVIORAL AUTHORIZATION");
        // Extract user context from the SecurityContextHolder or any custom authentication mechanism
        User user = extractUser();

        System.out.println("Behavioral aspect triggered on function: " + joinPoint.getSignature().getName());

        // Check if the user is authorized based on behavioral rules
        if (!isBehaviorallyAuthorized(user)) {
            System.out.println("-----------------------------------------------------------------------------");
            throw new AuthorizationException("User with role- " + user.getRole() + " is not allowed to access this endpoint.");
        }
        System.out.println("-----------------------------------------------------------------------------");
    }

    private User extractUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            return new User("UnauthorizedUser", Role.USER);
        }
    }

    private boolean isBehaviorallyAuthorized(User user) {
        return Role.ADMIN.equals(user.getRole());
    }
}
