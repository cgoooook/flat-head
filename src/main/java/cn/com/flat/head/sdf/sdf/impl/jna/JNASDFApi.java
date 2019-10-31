package cn.com.flat.head.sdf.sdf.impl.jna;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

import cn.com.flat.head.sdf.sdf.SDFConstants;
import cn.com.flat.head.sdf.sdf.impl.SDFConfigure;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;


public class JNASDFApi {
	private static SDFConfigure hsmConfig = null;
	private static String libPath = null;
	public JNASDFApi()
	{
		if ( hsmConfig == null) {
			hsmConfig = new SDFConfigure();
		}

		try {
			libPath = URLDecoder.decode(hsmConfig.getDLLPathConfig(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
	}
	
	public static class RSArefPublicKey extends Structure {
		public int bits;
		public byte[] m = new byte[SDFConstants.RSAref_MAX_LEN];
		public byte[] e = new byte[SDFConstants.RSAref_MAX_LEN];

		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { "bits", "m", "e" });
		}

		public static class ByReference extends RSArefPublicKey implements Structure.ByReference {
		}

		public static class ByValue extends RSArefPublicKey implements Structure.ByValue {
		}
	}

	public static class RSArefPrivateKey extends Structure {
		public int bits;
		public byte[] m = new byte[SDFConstants.RSAref_MAX_LEN];
		public byte[] e = new byte[SDFConstants.RSAref_MAX_LEN];
		public byte[] d = new byte[SDFConstants.RSAref_MAX_LEN];
		public byte[] prime1 = new byte[SDFConstants.RSAref_MAX_PLEN];
		public byte[] prime2 = new byte[SDFConstants.RSAref_MAX_PLEN];
		public byte[] pexp1 = new byte[SDFConstants.RSAref_MAX_PLEN];
		public byte[] pexp2 = new byte[SDFConstants.RSAref_MAX_PLEN];
		public byte[] coef = new byte[SDFConstants.RSAref_MAX_PLEN];

		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { "bits", "m", "e", "d", "prime1", "prime2", "pexp1", "pexp2", "coef" });
		}

		public static class ByReference extends RSArefPrivateKey implements Structure.ByReference {
		}

		public static class ByValue extends RSArefPrivateKey implements Structure.ByValue {
		}
	}

	public static class ECCrefPublicKey extends Structure {
		public int bits;
		public byte[] x = new byte[SDFConstants.ECCref_MAX_LEN];
		public byte[] y = new byte[SDFConstants.ECCref_MAX_LEN];

		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { "bits", "x", "y" });
		}

		public static class ByReference extends ECCrefPublicKey implements Structure.ByReference {
		}

		public static class ByValue extends ECCrefPublicKey implements Structure.ByValue {
		}
	}

	public static class ECCrefPrivateKey extends Structure {
		public int bits;
		public byte[] d = new byte[SDFConstants.ECCref_MAX_LEN];

		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { "bits", "d" });
		}

		public static class ByReference extends ECCrefPrivateKey implements Structure.ByReference {
		}

		public static class ByValue extends ECCrefPrivateKey implements Structure.ByValue {
		}
	}

	public static class ECCCipher extends Structure {
		public byte[] x = new byte[SDFConstants.ECCref_MAX_LEN];
		public byte[] y = new byte[SDFConstants.ECCref_MAX_LEN];
		public byte[] m = new byte[32];
		public int l;
		public byte[] c = new byte[hsmConfig.getSM2EncryptLengthMaxConfig()];

		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { "x", "y", "m", "l", "c" });
		}

		public static class ByReference extends ECCCipher implements Structure.ByReference {
		}

		public static class ByValue extends ECCCipher implements Structure.ByValue {
		}
	}

	public static class ECCSignature extends Structure {
		public byte[] r = new byte[SDFConstants.ECCref_MAX_LEN];
		public byte[] s = new byte[SDFConstants.ECCref_MAX_LEN];

		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { "r", "s" });
		}

		public static class ByReference extends ECCSignature implements Structure.ByReference {
		}

		public static class ByValue extends ECCSignature implements Structure.ByValue {
		}
	}

	public interface CLib extends Library {
		
		CLib INSTANCE = (CLib) Native.loadLibrary(libPath, CLib.class);

		int SDF_OpenDevice(PointerByReference ppDevHandle);

		int SDF_CloseDevice(Pointer pDevHandle);

		int SDF_OpenSession(Pointer pDevHandle, PointerByReference ppSessionHandle);

		int SDF_CloseSession(Pointer pSessionHandle);

		int SDF_GenerateRandom(Pointer pSessionHandle, int uiRandomLength, byte[] pucRandom);

		int SDF_GetPrivateKeyAccessRight(Pointer pSessionHandle, int keyIndex, byte[] pwd, int pwdLength);

		int SDF_ReleasePrivateKeyAccessRight(Pointer pSessionHandle, int keyIndex);

		// RSA operations
		int SDF_GenerateKeyPair_RSA(Pointer pSessionHandle, int uiKeyBits, RSArefPublicKey.ByReference pPublicKey,
                                    RSArefPrivateKey.ByReference pucPrivateKey);

		int SDF_ExportSignPublicKey_RSA(Pointer pSessionHandle, int iKeyIndex, RSArefPublicKey.ByReference pPublicKey);

		int SDF_ExportEncPublicKey_RSA(Pointer pSessionHandle, int iKeyIndex, RSArefPublicKey.ByReference pPublicKey);

		int SDF_ExternalPublicKeyOperation_RSA(Pointer pSessionHandle, RSArefPublicKey.ByReference pPublicKey,
                                               byte[] pInput, int inputLength, byte[] paOutput, IntByReference pOutputLength);

		int SDF_InternalPublicKeyOperation_RSA(Pointer pSessionHandle, int rsaPublKeyIndex, byte[] pInput,
                                               int inputLength, byte[] pOutput, IntByReference pOutputLength);

		int SDF_InternalPrivateKeyOperation_RSA(Pointer pSessionHandle, int rsaPrivKeyIndex, byte[] pInput,
                                                int inputLength, byte[] pOutput, IntByReference pOutputLength);

		// ecc operations
		int SDF_GenerateKeyPair_ECC(Pointer pSessionHandle, int iAlgID, ECCrefPublicKey.ByReference pPublicKey,
                                    ECCrefPrivateKey.ByReference pucPrivateKey);

		int SDF_ExportSignPublicKey_ECC(Pointer pSessionHandle, int iKeyIndex, ECCrefPublicKey.ByReference pPublicKey);

		int SDF_ExportEncPublicKey_ECC(Pointer pSessionHandle, int iKeyIndex, ECCrefPublicKey.ByReference pPublicKey);

		int SDF_InternalSign_ECC(Pointer pSessionHandle, int eccPrivKeyIndex, byte[] pInput, int inputLength,
                                 ECCSignature.ByReference pSignature);

		int SDF_InternalVerify_ECC(Pointer pSessionHandle, int eccPrivKeyIndex, byte[] pInput, int inputLength,
                                   ECCSignature.ByReference pSignature);
		
		int SDF_ExternalSign_ECC_Ex(Pointer pSessionHandle, int iAlgID, ECCrefPrivateKey.ByReference pucPrivateKey, byte[] pInput, int inputLength,
                                    ECCSignature.ByReference pSignature);

		int SDF_ExternalVerify_ECC(Pointer pSessionHandle, ECCrefPublicKey.ByReference pPublicKey, byte[] pInput,
                                   int inputLength, ECCSignature.ByReference pSignature);

		int SDF_ExternalEncrypt_ECC(Pointer pSessionHandle, int iAlgID, ECCrefPublicKey.ByReference pPublicKey,
                                    byte[] pInput, int inputLength, ECCCipher.ByReference pEncrypt);

		int SDF_InternalEncrypt_ECC(Pointer pSessionHandle, int iAlgID, int eccPublKeyIndex, byte[] pInput,
                                    int inputLength, ECCCipher.ByReference pEncrypt);
		
		int SDF_E_Decrypt_ECC(Pointer pSessionHandle, int iAlgID, int iKeyIndex, ECCrefPrivateKey.ByReference pucPrivateKey,
                              ECCCipher.ByReference pEncrypt, byte[] pOutput, IntByReference pOutputLength);

		// encrypt and decrypt
		int SDF_ImportKey(Pointer pDevHandle, byte[] pKey, int keyLength, PointerByReference ppKeyHandle);
		
		int SDF_Encrypt(Pointer pSessionHandle, Pointer pKeyHandle, int iAlgID, byte[] iv, byte[] pInput,
                        int inputLength, byte[] paOutput, IntByReference pOutputLength);

		int SDF_Decrypt(Pointer pSessionHandle, Pointer pKeyHandle, int iAlgID, byte[] iv, byte[] pInput,
                        int inputLength, byte[] paOutput, IntByReference pOutputLength);

		int SDF_DestroyKey(Pointer pSessionHandle, Pointer pKeyHandle);

		// digest
		int SDF_HashInit(Pointer pSessionHandle, int iAlgID, ECCrefPublicKey.ByReference pPublicKey, byte[] pUserID,
                         int userIDLength);

		int SDF_HashUpdate(Pointer pSessionHandle, byte[] pInput, int inputLength);

		int SDF_HashFinal(Pointer pSessionHandle, byte[] pDigest, IntByReference pDigestLength);

		// file
		int SDF_CreateFile(Pointer pSessionHandle, byte[] pFileName, int fileNameLength, int fileSize);

		int SDF_ReadFile(Pointer pSessionHandle, byte[] pFileName, int fileNameLength, int offset,
                         IntByReference readLength, byte[] readData);

		int SDF_WriteFile(Pointer pSessionHandle, byte[] pFileName, int fileNameLength, int offset, int writeLength,
                          byte[] writeData);

		int SDF_DeleteFile(Pointer pSessionHandle, byte[] pFileName, int fileNameLength);

	}
}
