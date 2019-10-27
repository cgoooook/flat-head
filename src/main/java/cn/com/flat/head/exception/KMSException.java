package cn.com.flat.head.exception;

/**
 * Created by panzhuowen on 2019/10/27.
 */
public class KMSException extends RuntimeException {

    private Throwable cause;

    public KMSException(String message) {
        super(message);
    }

    public KMSException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
