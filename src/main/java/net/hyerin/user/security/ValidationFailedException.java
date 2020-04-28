package net.hyerin.user.security;

import org.springframework.security.core.AuthenticationException;

public class ValidationFailedException extends AuthenticationException {

    public ValidationFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidationFailedException(String msg) {
        super(msg);
    }

}
