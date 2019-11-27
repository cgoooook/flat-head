package cn.com.flat.head.rest.exception;

public class FlatServiceException extends RuntimeException {

    private static final long serialVersionUID = 1400704734339484564L;

    private String errorCode;
    private Object[] errorArgs;
    private String errorMessage;

    public FlatServiceException(String message, String errorMessage, String errorCode, Object... errorArgs) {
        super(message);
        this.errorCode = errorCode;
        this.errorArgs = errorArgs;
        this.errorMessage = errorMessage;
    }

    public FlatServiceException(String message, Throwable cause, String errorCode, Object... errorArgs) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorArgs = errorArgs;
    }

    public FlatServiceException(String message, String errorCode, String errorMessage) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public FlatServiceException(String message, Throwable cause, String errorCode, String errorMessage) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public Object[] getErrorArgs() {
        return this.errorArgs;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }


}
