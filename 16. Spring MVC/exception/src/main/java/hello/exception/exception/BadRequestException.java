package hello.exception.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

@ResponseStatus(code = BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException{
//	ResponseStatusExceptionResolver

}
