package com.budgetku.backend.config;

import com.budgetku.backend.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCredentialValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserCredentialValidationException(UserCredentialValidationException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getErrors(),
                ex.getStatus().value(),
                ex.getErrorCode()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserIDNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(NifNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNifNotFoundException(NifNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(SupplierValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserCredentialsValidationException(SupplierValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getErrors(),
                ex.getStatus().value(),
                ex.getErrorCode()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplierNotFoundException(SupplierNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(BudgetTypeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBudgetTypeAlreadyExitsException(BudgetTypeAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(BudgetTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBudgetTypeNotFoundException(BudgetTypeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(BudgetSubtypeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBudgetSubtypeAlreadyExitsException(BudgetSubtypeAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(BudgetExceededException.class)
    public ResponseEntity<ErrorResponse> handleBudgetExceededException(BudgetExceededException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(MovementAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleMovementAlreadyExistsException(MovementAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(MovementValidationException.class)
    public ResponseEntity<ErrorResponse> handleMovementValidationException(MovementValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getErrors(),
                ex.getStatus().value(),
                ex.getErrorCode()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInvoiceNotFoundException(InvoiceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(FailedToUploadFileException.class)
    public ResponseEntity<ErrorResponse> handleFailedToUploadFileException(FailedToUploadFileException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(InvoiceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleInvoiceAlreadyExistsException(InvoiceAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(MovementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovementNotFoundException(MovementNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(DocumentNumberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDocumentNumberNotFoundException(DocumentNumberNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
