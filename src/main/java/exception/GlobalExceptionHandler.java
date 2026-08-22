package exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> authorityNotFoundHandler(AuthorityNotFoundException e) {
        return ResponseEntity.ok(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> roleNotFoundHandler(RoleNotFoundException e) {
        return ResponseEntity.ok(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> userNotFoundHandler(UserNotFoundException e) {
        return ResponseEntity.ok(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> RoomFoundHandler(UserNotFoundException e) {
        return ResponseEntity.ok(Map.of("message", e.getMessage()));
    }


}
