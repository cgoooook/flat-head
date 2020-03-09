package cn.com.flat.head.sdf.sdf;

import cn.com.flat.head.sdf.cipher.SKey;
import cn.com.flat.head.sdf.cipher.SMechanism;

import java.util.Hashtable;

public class Functions {
	protected static Hashtable errorCodeNames_;
	protected static Hashtable mechansimNames_;
	protected static Hashtable keyNames_;
	protected static Hashtable digestMechanisms_;
	protected static Hashtable encryptDecryptMechanisms_;
	protected static Hashtable cbcEncryptDecryptMechanisms_;
	protected static Hashtable keyPairGenerationMechanisms_;
	protected static Hashtable keyGenerationMechanisms_;

	protected static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	public static String toFullHexString(long value) {
		long currentValue = value;
		StringBuffer stringBuffer = new StringBuffer(16);
		for (int j = 0; j < 16; j++) {
			int currentDigit = (int) currentValue & 0xf;
			stringBuffer.append(HEX_DIGITS[currentDigit]);
			currentValue >>>= 4;
		}

		return stringBuffer.reverse().toString();
	}

	public static String getErrorConeName(int errorCode) {
		if (errorCodeNames_ == null) {
			Hashtable<Integer, String> errorCodeNames = new Hashtable<Integer, String>();
			errorCodeNames.put(new Integer(SDFConstants.SDR_OK), SDFConstants.NAME_SDR_OK);
			errorCodeNames.put(new Integer(SDFConstants.SDR_UNKNOWERR), SDFConstants.NAME_SDR_UNKNOWERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_NOTSUPPORT), SDFConstants.NAME_SDR_NOTSUPPORT);
			errorCodeNames.put(new Integer(SDFConstants.SDR_COMMFAIL), SDFConstants.NAME_SDR_COMMFAIL);
			errorCodeNames.put(new Integer(SDFConstants.SDR_HARDFAIL), SDFConstants.NAME_SDR_HARDFAIL);
			errorCodeNames.put(new Integer(SDFConstants.SDR_OPENDEVICE), SDFConstants.NAME_SDR_OPENDEVICE);
			errorCodeNames.put(new Integer(SDFConstants.SDR_OPENSESSION), SDFConstants.NAME_SDR_OPENSESSION);
			errorCodeNames.put(new Integer(SDFConstants.SDR_KEYNOTEXIST), SDFConstants.NAME_SDR_KEYNOTEXIST);
			errorCodeNames.put(new Integer(SDFConstants.SDR_ALGNOTSUPPORT), SDFConstants.NAME_SDR_ALGNOTSUPPORT);
			errorCodeNames.put(new Integer(SDFConstants.SDR_ALGMODNOTSUPPORT), SDFConstants.NAME_SDR_ALGMODNOTSUPPORT);
			errorCodeNames.put(new Integer(SDFConstants.SDR_PKOPERR), SDFConstants.NAME_SDR_PKOPERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_SKOPERR), SDFConstants.NAME_SDR_SKOPERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_SIGNERR), SDFConstants.NAME_SDR_SIGNERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_VERIFYERR), SDFConstants.NAME_SDR_VERIFYERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_SYMOPERR), SDFConstants.NAME_SDR_SYMOPERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_STEPERR), SDFConstants.NAME_SDR_STEPERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_FILESIZEERR), SDFConstants.NAME_SDR_FILESIZEERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_FILENOEXIST), SDFConstants.NAME_SDR_FILENOEXIST);
			errorCodeNames.put(new Integer(SDFConstants.SDR_FILEOFSERR), SDFConstants.NAME_SDR_FILEOFSERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_KEYTYPEERR), SDFConstants.NAME_SDR_KEYTYPEERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_KEYERR), SDFConstants.NAME_SDR_KEYERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_ENCDATAERR), SDFConstants.NAME_SDR_ENCDATAERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_RANDERR), SDFConstants.NAME_SDR_RANDERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_PRKRERR), SDFConstants.NAME_SDR_PRKRERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_MACERR), SDFConstants.NAME_SDR_MACERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_FILEEXITS), SDFConstants.NAME_SDR_FILEEXITS);
			errorCodeNames.put(new Integer(SDFConstants.SDR_FILEWRITEERR), SDFConstants.NAME_SDR_FILEWRITEERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_NUBUFFER), SDFConstants.NAME_SDR_NUBUFFER);
			errorCodeNames.put(new Integer(SDFConstants.SDR_INARGERR), SDFConstants.NAME_SDR_INARGERR);
			errorCodeNames.put(new Integer(SDFConstants.SDR_FILEWRITEERR), SDFConstants.NAME_SDR_OUTARGERR);
			errorCodeNames_ = errorCodeNames;
		}

		return (String) errorCodeNames_.get(new Integer(errorCode));
	}

	public static String mechanismCodeToString(int mechansimCode) {

		if (mechansimNames_ == null) {
			Hashtable<Integer, String> mechansimNames = new Hashtable<Integer, String>();
			mechansimNames.put(new Integer(SMechanism.SKM_SM2_KEY_PAIR_GEN), SMechanism.NAME_SKM_SM2_KEY_PAIR_GEN);
			mechansimNames.put(new Integer(SMechanism.SKM_SM2_SIGN), SMechanism.NAME_SKM_SM2_SIGN);
			mechansimNames.put(new Integer(SMechanism.SKM_SM2_ENC), SMechanism.NAME_SKM_SM2_ENC);
			mechansimNames.put(new Integer(SMechanism.SKM_SM1_KEY_GEN), SMechanism.NAME_SKM_SM1_KEY_GEN);
			mechansimNames.put(new Integer(SMechanism.SKM_SM1_ECB), SMechanism.NAME_SKM_SM1_ECB);
			mechansimNames.put(new Integer(SMechanism.SKM_SM1_CBC), SMechanism.NAME_SKM_SM1_CBC);
			mechansimNames.put(new Integer(SMechanism.SKM_SM4_KEY_GEN), SMechanism.NAME_SKM_SM4_KEY_GEN);
			mechansimNames.put(new Integer(SMechanism.SKM_SM4_ECB), SMechanism.NAME_SKM_SM4_ECB);
			mechansimNames.put(new Integer(SMechanism.SKM_SM4_CBC), SMechanism.NAME_SKM_SM4_CBC);
			mechansimNames.put(new Integer(SMechanism.SKM_MD5), SMechanism.NAME_SKM_MD5);
			mechansimNames.put(new Integer(SMechanism.SKM_SHA_1), SMechanism.NAME_SKM_SHA_1);
			mechansimNames.put(new Integer(SMechanism.SKM_SHA224), SMechanism.NAME_SKM_SHA224);
			mechansimNames.put(new Integer(SMechanism.SKM_SHA256), SMechanism.NAME_SKM_SHA256);
			mechansimNames.put(new Integer(SMechanism.SKM_SM3), SMechanism.NAME_SKM_SM3);
			mechansimNames.put(new Integer(SMechanism.SKM_SM3_USER_ID), SMechanism.NAME_SKM_SM3_USER_ID);
			mechansimNames_ = mechansimNames;
		}

		int mechansimCodeObject = new Integer(mechansimCode);
		Object entry = mechansimNames_.get(mechansimCodeObject);

		String mechanismName = (entry != null) ? entry.toString()
				: "Unknwon mechanism with code: 0x" + toFullHexString(mechansimCode);

		return mechanismName;
	}
	
	public static String keyCodeToString(int keyCode) {

		if (keyNames_ == null) {
			Hashtable<Integer, String> keyNames = new Hashtable<Integer, String>();
			keyNames.put(new Integer(SKey.SKK_SM2_PRIV_KEY), SKey.NAME_SKK_SM2_PRIV_KEY);
			keyNames.put(new Integer(SKey.SKK_SM2_PRIV_KEY_ID), SKey.NAME_SKK_SM2_PRIV_KEY_ID);
			keyNames.put(new Integer(SKey.SKK_SM2_PUBL_KEY), SKey.NAME_SKK_SM2_PUBL_KEY);
			keyNames.put(new Integer(SKey.SKK_SM2_PUBL_KEY_ID), SKey.NAME_SKK_SM2_PUBL_KEY_ID);
			keyNames.put(new Integer(SKey.SKK_SM1_KEY), SKey.NAME_SKK_SM1_KEY);
			keyNames.put(new Integer(SKey.SKK_SM1_KEY_ID), SKey.NAME_SKK_SM1_KEY_ID);
			keyNames.put(new Integer(SKey.SKK_SM4_KEY), SKey.NAME_SKK_SM4_KEY);
			keyNames.put(new Integer(SKey.SKK_SM4_KEY_ID), SKey.NAME_SKK_SM4_KEY_ID);
			keyNames_ = keyNames;
		}

		int keyCodeObject = new Integer(keyCode);
		Object entry = mechansimNames_.get(keyCodeObject);

		String keyName = (entry != null) ? entry.toString()
				: "Unknwon key with code: 0x" + toFullHexString(keyCode);

		return keyName;
	}

	public static boolean isDigestMechanism(int mechanismCode) {
		// build the hashtable on demand (=first use)
		if (digestMechanisms_ == null) {
			Hashtable<Integer, String> digestMechanisms = new Hashtable<Integer, String>();
			digestMechanisms.put(new Integer(SMechanism.SKM_MD5), SMechanism.NAME_SKM_MD5);
			digestMechanisms.put(new Integer(SMechanism.SKM_SHA_1), SMechanism.NAME_SKM_SHA_1);
			digestMechanisms.put(new Integer(SMechanism.SKM_SHA224), SMechanism.NAME_SKM_SHA224);
			digestMechanisms.put(new Integer(SMechanism.SKM_SHA256), SMechanism.NAME_SKM_SHA256);
			digestMechanisms.put(new Integer(SMechanism.SKM_SM3), SMechanism.NAME_SKM_SM3);
			digestMechanisms.put(new Integer(SMechanism.SKM_SM3_USER_ID), SMechanism.NAME_SKM_SM3_USER_ID);
			digestMechanisms = digestMechanisms;
		}

		return digestMechanisms_.containsKey(new Integer(mechanismCode));
	}

	public static boolean isEncryptDecryptMechanism(int mechanismCode) {
		if (encryptDecryptMechanisms_ == null) {
			Hashtable<Integer, String> encryptDecryptMechanisms = new Hashtable<Integer, String>();
			encryptDecryptMechanisms.put(new Integer(SMechanism.SKM_SM1_ECB), SMechanism.NAME_SKM_SM1_ECB);
			encryptDecryptMechanisms.put(new Integer(SMechanism.SKM_SM1_CBC), SMechanism.NAME_SKM_SM1_CBC);
			encryptDecryptMechanisms.put(new Integer(SMechanism.SKM_SM4_ECB), SMechanism.NAME_SKM_SM4_ECB);
			encryptDecryptMechanisms.put(new Integer(SMechanism.SKM_SM4_CBC), SMechanism.NAME_SKM_SM4_CBC);
			encryptDecryptMechanisms_ = encryptDecryptMechanisms;
		}

		return encryptDecryptMechanisms_.containsKey(new Integer(mechanismCode));
	}

	public static boolean isCBCEncryptDecryptMechanism(int mechanismCode) {
		if (cbcEncryptDecryptMechanisms_ == null) {
			Hashtable<Integer, String> cbcEncryptDecryptMechanisms = new Hashtable<Integer, String>();
			cbcEncryptDecryptMechanisms.put(new Integer(SMechanism.SKM_SM1_CBC), SMechanism.NAME_SKM_SM1_CBC);
			cbcEncryptDecryptMechanisms.put(new Integer(SMechanism.SKM_SM4_CBC), SMechanism.NAME_SKM_SM4_CBC);
			cbcEncryptDecryptMechanisms_ = cbcEncryptDecryptMechanisms;
		}

		return cbcEncryptDecryptMechanisms_.containsKey(new Integer(mechanismCode));
	}

	public static boolean isKeyGenerationMechanism(int mechanismCode) {
		if (keyGenerationMechanisms_ == null) {
			Hashtable<Integer, String> keyGenerationMechanisms = new Hashtable<Integer, String>();
			keyGenerationMechanisms.put(new Integer(SMechanism.SKM_SM1_KEY_GEN), SMechanism.NAME_SKM_SM1_KEY_GEN);
			keyGenerationMechanisms.put(new Integer(SMechanism.SKM_SM4_KEY_GEN), SMechanism.NAME_SKM_SM4_KEY_GEN);
			keyGenerationMechanisms_ = keyGenerationMechanisms;
		}

		return keyGenerationMechanisms_.containsKey(new Integer(mechanismCode));
	}

	public static boolean isKeyPairGenerationMechanism(int mechanismCode) {
		if (keyPairGenerationMechanisms_ == null) {
			Hashtable<Integer, String> keyPairGenerationMechanisms = new Hashtable<Integer, String>();
			keyPairGenerationMechanisms.put(new Integer(SMechanism.SKM_SM2_KEY_PAIR_GEN), SMechanism.NAME_SKM_SM2_KEY_PAIR_GEN);
			keyPairGenerationMechanisms_ = keyPairGenerationMechanisms;
		}

		return keyPairGenerationMechanisms_.containsKey(new Integer(mechanismCode));
	}
}
