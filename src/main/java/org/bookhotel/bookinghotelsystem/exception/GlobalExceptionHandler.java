package org.bookhotel.bookinghotelsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> authorityNotFoundHandler(AuthorityNotFoundException e) {
        return (Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> roleNotFoundHandler(RoleNotFoundException e) {
        return(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> userNotFoundHandler(UserNotFoundException e) {
        return (Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> RoomNotFoundHandler(RoomNotFoundException e) {
        return (Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bookingNotFoundHandler(BookingNotFoundException e) {
        return (Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(NoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> NoAccessHandler(NoAccessException e) {
        return (Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(BookingConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> bookingConflictHandler(BookingConflictException e) {
        return (Map.of("message", e.getMessage()));
    }


}
