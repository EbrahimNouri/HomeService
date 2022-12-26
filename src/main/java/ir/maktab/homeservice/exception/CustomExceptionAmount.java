package ir.maktab.homeservice.exception;

public class CustomExceptionAmount extends RuntimeException {
    public CustomExceptionAmount() {
    }

    public CustomExceptionAmount(String message) {
        super(message);
    }

    public CustomExceptionAmount(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionAmount(Throwable cause) {
        super(cause);
    }

    public CustomExceptionAmount(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
