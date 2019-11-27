package cn.com.flat.head.rest.exception;

public class FlatException extends RuntimeException {

    private static final long serialVersionUID = 3407902822755168730L;

    public FlatException() {
    }

    public FlatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FlatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlatException(String message) {
        super(message);
    }

    public FlatException(Throwable cause) {
        super(cause);
    }
}
