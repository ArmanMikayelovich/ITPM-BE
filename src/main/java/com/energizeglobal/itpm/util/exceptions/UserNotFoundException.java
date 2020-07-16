package com.energizeglobal.itpm.util.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with id: " + id + " not found.");
    }

}
