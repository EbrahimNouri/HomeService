package ir.maktab.homeservice.exception;

public class CustomExceptionUpdate extends RuntimeException{
    public CustomExceptionUpdate() {
    }

    public CustomExceptionUpdate(String message) {
        super(message);
    }

    public CustomExceptionUpdate(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionUpdate(Throwable cause) {
        super(cause);
    }

    public CustomExceptionUpdate(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
