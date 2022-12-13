package ir.maktab.homeservice.exception;

public class CustomPatternInvalidException extends RuntimeException{
    public CustomPatternInvalidException() {
    }

    public CustomPatternInvalidException(String message) {
        super(message);
    }

    public CustomPatternInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomPatternInvalidException(Throwable cause) {
        super(cause);
    }

    public CustomPatternInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
