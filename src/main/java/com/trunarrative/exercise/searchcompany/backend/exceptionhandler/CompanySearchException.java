package com.trunarrative.exercise.searchcompany.backend.exceptionhandler;

/**
 * Exception class in order to extend the runtime exception
 */
public class CompanySearchException extends RuntimeException {

    public CompanySearchException(String message) {
        super(message);
    }
}
