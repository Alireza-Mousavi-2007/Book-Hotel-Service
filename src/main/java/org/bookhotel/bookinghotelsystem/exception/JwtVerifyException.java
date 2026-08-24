package org.bookhotel.bookinghotelsystem.exception;


import org.jspecify.annotations.Nullable;
import org.springframework.security.core.AuthenticationException;

public class JwtVerifyException extends AuthenticationException {

    public JwtVerifyException(@Nullable String msg) {
        super(msg);
    }
}
