package cn.com.flat.head.sdf.sdf;

public class SDFException extends Exception {
	protected int errorCode_;

	public SDFException() {
		super();
	}

	public SDFException(String message) {
		super(message);
	}

	public SDFException(int errorCode) {
		errorCode_ = errorCode;
	}

	public synchronized String getMessage() {
		String errorCodeHexString = "0x" + Functions.toFullHexString((int) errorCode_);
		String errorCodeName = Functions.getErrorConeName(errorCode_);
		String message = (errorCodeName != null) ? errorCodeName : errorCodeHexString;

		return message;
	}

	public int getErrorCode() {
		return errorCode_;
	}
}
