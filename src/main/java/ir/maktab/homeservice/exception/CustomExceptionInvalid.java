package ir.maktab.homeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomExceptionInvalid extends RuntimeException{
    public CustomExceptionInvalid() {
    }

    public CustomExceptionInvalid(String message) {
        super(message);
    }

    public CustomExceptionInvalid(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionInvalid(Throwable cause) {
        super(cause);
    }

    public CustomExceptionInvalid(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
