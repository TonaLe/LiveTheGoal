package app.security.Event;

import app.security.DTO.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

@RestControllerAdvice
public class ErrorHandler {

//    @ExceptionHandler(SQLException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorDto handleSqlException(SQLException e, WebRequest webRequest) {
//        return new ErrorDto("ERROR_100", e.getLocalizedMessage());
//    }
}
