package cn.com.flat.head.sdf.cipher;


import cn.com.flat.head.sdf.sdf.Functions;
import cn.com.flat.head.sdf.util.Constants;

public class SMechanism implements Cloneable {
	// Mechanism (need modify)
	public static final int SKM_SM2_KEY_PAIR_GEN           = 0x00020100;    
	public static final int SKM_SM2_SIGN                   = 0x00000007;    
	public static final int SKM_SM2_ENC                    = 0x00020200;    
	public static final int SKM_SM1_KEY_GEN                = 0x00000100;    
	public static final int SKM_SM1_ECB                    = 0x00000101;    
	public static final int SKM_SM1_CBC                    = 0x00000102;    
	public static final int SKM_SM4_KEY_GEN                = 0x00000400;    
	public static final int SKM_SM4_ECB                    = 0x00000401;    
	public static final int SKM_SM4_CBC                    = 0x00000402;    
	public static final int SKM_MD5                        = 0x00000008;    
	public static final int SKM_SHA_1                      = 0x00000002;    
	public static final int SKM_SHA224                     = 0x00000010;    
	public static final int SKM_SHA256                     = 0x00000004;    
	public static final int SKM_SM3                        = 0x00000001;    
	public static final int SKM_SM3_USER_ID                = 0x00000001;    
	
	public static final String NAME_SKM_SM2_KEY_PAIR_GEN   = "SKM_SM2_KEY_PAIR_GEN";
	public static final String NAME_SKM_SM2_SIGN           = "SKM_SM2_SIGN";
	public static final String NAME_SKM_SM2_ENC            = "SKM_SM2_ENC";
	public static final String NAME_SKM_SM1_KEY_GEN        = "SKM_SM1_KEY_GEN";
	public static final String NAME_SKM_SM1_ECB            = "SKM_SM1_ECB";
	public static final String NAME_SKM_SM1_CBC            = "SKM_SM1_CBC";
	public static final String NAME_SKM_SM4_KEY_GEN        = "SKM_SM4_KEY_GEN";
	public static final String NAME_SKM_SM4_ECB            = "SKM_SM4_ECB";
	public static final String NAME_SKM_SM4_CBC            = "SKM_SM4_CBC";
	public static final String NAME_SKM_MD5                = "SKM_MD5";
	public static final String NAME_SKM_SHA_1              = "SKM_SHA_1";
	public static final String NAME_SKM_SHA224             = "SKM_SHA224";
	public static final String NAME_SKM_SHA256             = "SKM_SHA256";
	public static final String NAME_SKM_SM3                = "SKM_SM3";
	public static final String NAME_SKM_SM3_USER_ID        = "SKM_SM3_USER_ID";
	
	private int mechanismCode_;
	private Object parameters_;

	public SMechanism(int mechanismCode) {
		mechanismCode_ = mechanismCode;
	}

	public static SMechanism get(int mechanismCode) {
		return new SMechanism(mechanismCode);
	}

	public Object clone() {
		SMechanism clone = null;

		try {
			clone = (SMechanism) super.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen according to Java specifications
		}

		return clone;
	}

	public boolean equals(Object otherObject) {
		boolean euqal = false;

		if (otherObject instanceof SMechanism) {
			SMechanism other = (SMechanism) otherObject;
			euqal = (this == other) || (this.mechanismCode_ == other.mechanismCode_)
					&& (((this.parameters_ == null) && other.parameters_ == null)
							|| ((this.parameters_ != null) && this.parameters_.equals(other.parameters_)));
		}

		return euqal;
	}

	public Object getParameters() {
		return parameters_;
	}

	public void setParameters(Object parameters) {
		parameters_ = parameters;
	}

	public int getMechanismCode() {
		return mechanismCode_;
	}

	public String getName() {
		return Functions.mechanismCodeToString(mechanismCode_);
	}

	public boolean isDigestMechanism() {
		return Functions.isDigestMechanism(mechanismCode_);
	}

	public boolean isCBCEncryptDecryptMechanism() {
		return Functions.isCBCEncryptDecryptMechanism(mechanismCode_);
	}

	public boolean isEncryptDecryptMechanism() {
		return Functions.isEncryptDecryptMechanism(mechanismCode_);
	}

	public boolean isKeyGenerationMechanism() {
		return Functions.isKeyGenerationMechanism(mechanismCode_);
	}

	public boolean isKeyPairGenerationMechanism() {
		return Functions.isKeyPairGenerationMechanism(mechanismCode_);
	}
	
	public boolean isSM2SignMechanism() {
		return mechanismCode_ == SKM_SM2_SIGN ? true:false;
	}
	
	public boolean isSM2EncryptMechanism() {
		return mechanismCode_ == SKM_SM2_ENC ? true:false;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(128);

		buffer.append(Constants.INDENT);
		buffer.append("Mechanism: ");
		buffer.append(Functions.mechanismCodeToString(mechanismCode_));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Parameters: ");
		buffer.append(Constants.NEWLINE);
		buffer.append(parameters_);

		return buffer.toString();
	}
}
