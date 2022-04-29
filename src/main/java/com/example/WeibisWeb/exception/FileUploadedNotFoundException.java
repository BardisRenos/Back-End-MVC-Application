package com.example.WeibisWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class handles User Not Found Exception cases.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileUploadedNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * This method return an exception message when the FileUploaded not found.
     * @param message The given message what the exception will print.
     */
    public FileUploadedNotFoundException(String message) {
        super(message);
    }
}
