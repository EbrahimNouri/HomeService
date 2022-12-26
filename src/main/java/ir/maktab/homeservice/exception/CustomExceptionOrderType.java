package ir.maktab.homeservice.exception;

public class CustomExceptionOrderType extends RuntimeException{
    public CustomExceptionOrderType() {
    }

    public CustomExceptionOrderType(String message) {
        super(message);
    }

    public CustomExceptionOrderType(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionOrderType(Throwable cause) {
        super(cause);
    }

    public CustomExceptionOrderType(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
