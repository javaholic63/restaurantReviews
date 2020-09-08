package com.powerreviews.project.errors;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.powerreviews.project.errors.CustomErrorResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


	@Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, String> body = new LinkedHashMap<String, String>();
        body.put("timestamp", LocalDate.now().toString());
        body.put("status", String.valueOf(status.value()));

        //Get all errors
        List<String> errors = (List<String>) ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors.toString());

        return new ResponseEntity<>(body, headers, status);
	}
	
	@ExceptionHandler({ConstraintViolationException.class, PSQLException.class})
	public void restaurantNotExist(HttpServletResponse response) throws IOException
    {
		response.sendError(HttpStatus.BAD_REQUEST.value());
    }
	

	@ExceptionHandler({BadUserNameException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<CustomErrorResponse> customHandleBadUser(Exception ex) {

		log.error("Invalid user name Request");

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.BAD_REQUEST.value());

		log.debug("Status: " + errors.getStatus());

		return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);

	}

}
