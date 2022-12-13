package ir.maktab.homeservice.exception;

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
