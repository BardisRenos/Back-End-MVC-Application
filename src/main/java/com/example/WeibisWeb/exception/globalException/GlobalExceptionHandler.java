package com.example.WeibisWeb.exception.globalException;

import com.example.WeibisWeb.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The global exceptions' handler. This class handles all the exceptions for each controller.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles Client class exceptions
     * @param ex the exception
     * @return ResponseEntity
     */
    @ExceptionHandler({ClientNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundClient(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles Candidate class exceptions
     * @param ex the exception
     * @return ResponseEntity
     */
    @ExceptionHandler({CandidateNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundCandidate(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles JobDescription class exceptions
     * @param ex the exception
     * @return ResponseEntity
     */
    @ExceptionHandler({JobDescriptionNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundJobDescription(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles User class exceptions
     * @param ex the exception
     * @return ResponseEntity
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundUser(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles File class exceptions
     * @param ex the exception
     * @return ResponseEntity
     */
    @ExceptionHandler({FileUploadedNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundFileUploaded(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
