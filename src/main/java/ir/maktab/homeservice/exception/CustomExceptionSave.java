package ir.maktab.homeservice.exception;

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
