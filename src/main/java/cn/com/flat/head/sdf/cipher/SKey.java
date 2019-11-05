package cn.com.flat.head.sdf.cipher;


import cn.com.flat.head.sdf.sdf.Functions;
import cn.com.flat.head.sdf.util.Constants;

public class SKey {
	public static final int SKK_SM2_PRIV_KEY    = 0x00000001;
	public static final int SKK_SM2_PRIV_KEY_ID = 0x00000002;
	public static final int SKK_SM2_PUBL_KEY    = 0x00000003;
	public static final int SKK_SM2_PUBL_KEY_ID = 0x00000004;

	public static final int SKK_SM1_KEY         = 0x00000005;
	public static final int SKK_SM1_KEY_ID      = 0x00000006;
	public static final int SKK_SM4_KEY         = 0x00000007;
	public static final int SKK_SM4_KEY_ID      = 0x00000008;

	public static final String NAME_SKK_SM2_PRIV_KEY      = "SKK_SM2_PRIV_KEY";
	public static final String NAME_SKK_SM2_PRIV_KEY_ID   = "SKK_SM2_PRIV_KEY_ID";
	public static final String NAME_SKK_SM2_PUBL_KEY      = "SKK_SM2_PUBL_KEY";
	public static final String NAME_SKK_SM2_PUBL_KEY_ID   = "SKK_SM2_PUBL_KEY_ID";
	public static final String NAME_SKK_SM1_KEY           = "SKK_SM1_KEY";
	public static final String NAME_SKK_SM1_KEY_ID        = "SKK_SM1_KEY_ID";
	public static final String NAME_SKK_SM4_KEY           = "SKK_SM4_KEY";
	public static final String NAME_SKK_SM4_KEY_ID        = "SKK_SM4_KEY_ID";

	private int keyType;
	private byte[] key;
	private int keyID;
	private String keyLabel;

	public SKey(int keyType, String keyLabel, int keyID) {
		this.keyType = keyType;
		this.keyLabel = keyLabel;
		this.keyID = keyID;
		this.key = null;
	}

	public SKey(int keyType, String keyLabel, byte[] key) {
		this.keyType = keyType;
		this.keyLabel = keyLabel;
		this.key = key;
		this.keyID = 0;
	}

	public SKey(int keyType, byte[] key) {
		this.keyType = keyType;
		this.keyLabel = null;
		this.key = key;
		this.keyID = 0;
	}

	public SKey() {
		this.keyID = 0;
	}

	public SKey(int keyType, int keyID) {
		this.keyType = keyType;
		this.keyLabel = null;
		this.keyID = keyID;
		this.key = null;
	}

	public SKey(int keyType) {
		this.keyType = keyType;
		this.keyLabel = null;
		this.keyID = 0;
		this.key = null;
	}

	public static SKey get(int keyTypeCode) {
		return new SKey(keyTypeCode);
	}

	public byte[] getKey() {
		return this.key;
	}

	public String getKeyLabel() {
		return this.keyLabel;
	}

	public int getKeyID() {
		return this.keyID;
	}

	public int getKeyType() {
		return this.keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}

	public void setKeyLabel(String keyLabel) {
		this.keyLabel = keyLabel;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public void setKeyID(int keyID) {
		this.keyID = keyID;
	}
	
	public String getName() {
		return Functions.keyCodeToString(keyType);
	}

	public boolean isSM2PublKey() {
		if ( keyType != SKK_SM2_PUBL_KEY && keyType != SKK_SM2_PUBL_KEY_ID )
			return false;
		
		return true;
	}
	
	public boolean isSM2PrivKey() {
		if ( keyType != SKK_SM2_PRIV_KEY && keyType != SKK_SM2_PRIV_KEY_ID )
			return false;
		
		return true;
	}

	public boolean isSymKey() {
		if ( keyType != SKK_SM1_KEY && keyType != SKK_SM4_KEY )
			return false;
		
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(128);

		buffer.append(Constants.INDENT);
		buffer.append("KeyType: ");
		buffer.append(Functions.keyCodeToString(keyType));
		buffer.append(Constants.NEWLINE);
		return buffer.toString();
	}	
}
