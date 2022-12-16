package ir.maktab.homeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomExceptionSave extends RuntimeException{
    public CustomExceptionSave() {
    }

    public CustomExceptionSave(String message) {
        super(message);
    }

    public CustomExceptionSave(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionSave(Throwable cause) {
        super(cause);
    }

    public CustomExceptionSave(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
