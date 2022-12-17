package ir.maktab.homeservice.exception;

public class CustomNotChoosingException extends RuntimeException{
    public CustomNotChoosingException() {
    }

    public CustomNotChoosingException(String message) {
        super(message);
    }

    public CustomNotChoosingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomNotChoosingException(Throwable cause) {
        super(cause);
    }

    public CustomNotChoosingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
