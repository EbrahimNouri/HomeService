package ir.maktab.homeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class CustomExceptionNotFind extends RuntimeException{
    public CustomExceptionNotFind() {
    }

    public CustomExceptionNotFind(String message) {
        super(message);
    }

    public CustomExceptionNotFind(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionNotFind(Throwable cause) {
        super(cause);
    }

    public CustomExceptionNotFind(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
