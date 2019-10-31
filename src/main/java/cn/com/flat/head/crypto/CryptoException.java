package cn.com.flat.head.crypto;

/**
 * Created by panzhuowen on 2019/10/28.
 */
public class CryptoException extends RuntimeException {

    private Throwable cause;

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
