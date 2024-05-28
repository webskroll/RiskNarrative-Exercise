package com.trunarrative.exercise.searchcompany.web.exceptionhandler;

import com.trunarrative.exercise.searchcompany.backend.exceptionhandler.CompanySearchException;
import com.trunarrative.exercise.searchcompany.web.model.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * This handles when request does not have manadatory fields
     * @param request of type HttpServletRequest
     * @param exception of type MissingRequestValueException
     * @return ErrorResponse
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestValueException.class)
    ErrorResponse handleException(HttpServletRequest request, MissingRequestValueException exception) {
        return new ErrorResponse(request.getRequestURI(),
                exception.getLocalizedMessage());
    }

    /**
     * Error when resource not found
     * @param request of type HttpServletRequest
     * @param exception of type MissingRequestValueException
     * @return ErrorResponse
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CompanySearchException.class) ErrorResponse
    handleException(HttpServletRequest request, CompanySearchException exception) {
        return new ErrorResponse(request.getRequestURI(),
                exception.getMessage());
    }

    /**
     * Error when application throws internal server error.
     * @param request of type HttpServletRequest
     * @param exception of type MissingRequestValueException
     * @return ErrorResponse
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler ErrorResponse
    handleException(final HttpServletRequest request, final Exception exception) {
        log.error("Error while finding details", exception);
        return new ErrorResponse(request.getRequestURI(),
                "Something go wrong !");
    }
}
