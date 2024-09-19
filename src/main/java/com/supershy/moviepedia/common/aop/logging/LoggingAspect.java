package com.supershy.moviepedia.common.aop.logging;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.supershy.moviepedia.review.controller..*(..))")
    public void logPointCut() {}

    @Before("logPointCut()")
    public void logMethodCall(JoinPoint jointPoint) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = "Anonymous";
        }
        String methodName = jointPoint.getSignature().getName();
        logger.info("User '{}' called method '{}'", userName, methodName);

    }
}
