package blogger.controller.error;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Map<String, String>> handleNoSuchElementException (NoSuchElementException e) {
		 
		
		// Log the error using SLF4J logger
        log.error("Error occurred: {}", e.getMessage());

        // Create a response map with a single entry
        Map<String, String> response = new HashMap<>();
        response.put("message", e.toString());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
}
