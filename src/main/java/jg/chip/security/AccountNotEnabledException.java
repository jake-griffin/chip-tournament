package jg.chip.security;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class AccountNotEnabledException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = -1126699074574529145L;

    public AccountNotEnabledException(String message) {
        super(message);
    }
}
