package cn.com.flat.head.sdf.sdf;

import cn.com.flat.head.sdf.Session;
import cn.com.flat.head.sdf.cipher.SDigest;
import cn.com.flat.head.sdf.cipher.SKey;
import cn.com.flat.head.sdf.cipher.SKeyPair;
import cn.com.flat.head.sdf.cipher.SMechanism;
import cn.com.flat.head.sdf.parameter.CBCParam;
import cn.com.flat.head.sdf.sdf.impl.SDFImpl;

public class SDFSession implements Session {
	
	private SDFImpl hsmImplAPI = null;
	
	public SDFSession() throws Exception {
		if ( hsmImplAPI != null )
			return ;
		
		hsmImplAPI = new SDFImpl();
		hsmImplAPI.Impl_init();
	}

	@Override
	public byte[] sign(SMechanism mechanism, SKey privKey, byte[] input) throws Exception {
		if (mechanism == null || privKey == null || input == null ) {
			throw new NullPointerException("Session sign parameter was null.");
		}
		
		if ( !mechanism.isSM2SignMechanism() ) {
			throw new IllegalArgumentException("Session Sign mechanism " + mechanism.getMechanismCode() + " invalid.");
		}
		
		if ( false == privKey.isSM2PrivKey() ) {
			throw new IllegalArgumentException("Session Sign private key " + privKey.getName() + " invalid.");
		}
		
		// check input length. need modify
		byte[] signature = hsmImplAPI.IMPL_SM2Sign(privKey, input);
		
		return signature;
	}

	@Override
	public boolean verifySign(SMechanism mechanism, SKey publKey, byte[] input, byte[] signaure) throws Exception {
		if (mechanism == null || publKey == null || input == null || signaure == null ) {
			throw new NullPointerException("Session verifySign parameter was null.");
		}
		
		if ( !mechanism.isSM2SignMechanism() ) {
			throw new IllegalArgumentException("Session verifySign mechanism " + mechanism.getMechanismCode() + " invalid.");
		}
		
		if ( false == publKey.isSM2PublKey() ) {
			throw new IllegalArgumentException("Session verifySign public key " + publKey.getName() + " invalid.");
		}
		
		boolean bln = hsmImplAPI.IMPL_SM2Verify(publKey, input, signaure);

		return bln;
	}

	@Override
	public byte[] encrypt(SMechanism mechanism, SKey encryptKey, byte[] input) throws Exception {
		if (mechanism == null || encryptKey == null || input == null  ) {
			throw new NullPointerException("Session encrypt parameter was null.");
		}
		
		// check key
		if ( false == encryptKey.isSM2PublKey() && false == encryptKey.isSymKey() ) {
			throw new IllegalArgumentException("Session encrypt key " + encryptKey.getName() + " invalid.");
		}
		
		// check mechanism 
		if ( !mechanism.isSM2EncryptMechanism() && !mechanism.isEncryptDecryptMechanism()) {
			throw new IllegalArgumentException("Session encrypt mechanism " + mechanism.getMechanismCode() + " invalid.");
		}
		
		byte[] encrypted = null;
		
		// sm2 encrypt
		if ( encryptKey.isSM2PublKey() ) {
			if ( !mechanism.isSM2EncryptMechanism() ) {
				throw new IllegalArgumentException("Session sm2 encrypt mechanism " + mechanism.getMechanismCode() + " invalid.");				
			}
			
			encrypted = hsmImplAPI.IMPL_SM2Encrypt(encryptKey, input);
		} else {
			byte[] cbcIv = null;
			if ( !mechanism.isEncryptDecryptMechanism() ) {
				throw new IllegalArgumentException("Session sym key encrypt mechanism " + mechanism.getMechanismCode() + " invalid.");				
			}
			
			if ( mechanism.isCBCEncryptDecryptMechanism() ) {
				CBCParam cbcParam = (CBCParam) mechanism.getParameters();
				if (cbcParam != null) {
					cbcIv = cbcParam.getIv();
				}
				
				if (cbcIv == null) {
					throw new Exception("Invalid initialization vector.");
				}
			}
			
			encrypted = hsmImplAPI.IMPL_Encrypt(mechanism.getMechanismCode(), encryptKey, cbcIv, input);
		}
		
		return encrypted;
	}

	@Override
	public byte[] decrypt(SMechanism mechanism, SKey decryptKey, byte[] encrypted) throws Exception {
		if (mechanism == null || decryptKey == null || encrypted == null  ) {
			throw new NullPointerException("Session decrypt parameter was null.");
		}
		
		// check key
		if ( false == decryptKey.isSM2PrivKey() && false == decryptKey.isSymKey() ) {
			throw new IllegalArgumentException("Session decrypt key " + decryptKey.getName() + " invalid.");
		}
		
		// check mechanism 
		if ( !mechanism.isSM2EncryptMechanism() && !mechanism.isEncryptDecryptMechanism()) {
			throw new IllegalArgumentException("Session decrypt mechanism " + mechanism.getName() + " invalid.");
		}
		
		byte[] decrypted = null;
		
		// sm2 encrypt
		if ( decryptKey.isSM2PublKey() ) {
			if ( !mechanism.isSM2EncryptMechanism() ) {
				throw new IllegalArgumentException("Session sm2 decrypt mechanism " + mechanism.getMechanismCode() + " invalid.");				
			}
			
			decrypted = hsmImplAPI.IMPL_SM2Decrypt(decryptKey, encrypted);
		} else {
			byte[] cbcIv = null;
			if ( !mechanism.isEncryptDecryptMechanism() ) {
				throw new IllegalArgumentException("Session sym key decrypt mechanism " + mechanism.getMechanismCode() + " invalid.");				
			}
			
			if ( mechanism.isCBCEncryptDecryptMechanism() ) {
				CBCParam cbcParam = (CBCParam) mechanism.getParameters();
				if (cbcParam != null) {
					cbcIv = cbcParam.getIv();
				}
				
				if (cbcIv == null) {
					throw new Exception("Invalid initialization vector.");
				}
			}
			
			decrypted = hsmImplAPI.IMPL_Decrypt(mechanism.getMechanismCode(), decryptKey, cbcIv, encrypted);
		}
				
		return decrypted;
	}

	@Override
	public SKey generateKey(SMechanism mechanism, int keyBits) throws Exception {
		if (mechanism == null) {
			throw new NullPointerException("Session generateKey parameter was null.");
		}
		
		// check mechanism 
		if ( !mechanism.isKeyGenerationMechanism()) {
			throw new IllegalArgumentException("Session Generate key mechanism " + mechanism.getMechanismCode() + " invalid.");
		}

		if ( mechanism.getMechanismCode() == SMechanism.SKM_SM1_KEY_GEN || mechanism.getMechanismCode() == SMechanism.SKM_SM4_KEY_GEN ) {
			if ( keyBits != 128 )
				throw new IllegalArgumentException("Session Generate key bits " + keyBits + " invalid.");
		}
		
		int keyLength = (keyBits+7)/8;
		byte[] keyData = hsmImplAPI.IMPL_GenerateRandom(keyLength);
		
		SKey symKey = null; 
		if ( mechanism.getMechanismCode() == SMechanism.SKM_SM1_KEY_GEN ) {
			symKey = SKey.get(SKey.SKK_SM1_KEY);
		} else {
			symKey = SKey.get(SKey.SKK_SM1_KEY);
		}
		
		symKey.setKey(keyData);
		
		return symKey;
	}

	@Override
	public SKeyPair generateKeyPair(SMechanism mechanism, int keyBits) throws Exception {
		if (mechanism == null) {
			throw new NullPointerException("Session generateKeyPair parameter was null.");
		}
		
		SKeyPair sm2KeyPair = null;
		if ( mechanism.getMechanismCode() == SMechanism.SKM_SM2_KEY_PAIR_GEN ) {
			if ( keyBits != SDFConstants.SM2_KEY_BITES ) {
				throw new IllegalArgumentException("Session sm2 key pair bits " + keyBits + " invalid.");							
			}
			
			sm2KeyPair = hsmImplAPI.IMPL_GenerateKeyPair_SM2(keyBits);
		} else {
			throw new IllegalArgumentException("Session SM2 key pair mechanism " + mechanism.getMechanismCode() + " invalid.");			
		}
		
		return sm2KeyPair;
	}

	@Override
	public SKeyPair findKeyPair(SMechanism mechanism, int keyIndex) throws Exception {
		if (mechanism == null) {
			throw new NullPointerException("Session findKeyPair parameter was null.");
		}
		
		SKeyPair keyPair = null;
		if ( mechanism.getMechanismCode() == SMechanism.SKM_SM2_KEY_PAIR_GEN ) {			
			SKey sm2PubKey = hsmImplAPI.IMPL_ExportSignPublicKey_SM2(keyIndex);
			
			SKey sm2PriKey = SKey.get(SKey.SKK_SM2_PRIV_KEY_ID);
			sm2PriKey.setKeyID(keyIndex);
			
			keyPair = new SKeyPair(sm2PubKey, sm2PriKey);
		} else {
			throw new IllegalArgumentException("Session find key pair mechanism " + mechanism.getMechanismCode() + " invalid.");			
		}
		
		return keyPair;
	}

	@Override
	public byte[] generateRandom(int randomLength) throws Exception {
		return hsmImplAPI.IMPL_GenerateRandom(randomLength);
	}

	@Override
	public SDigest digestInit(SMechanism mechanism, SKey sm2PublKey, byte[] userID) throws Exception {
		if (mechanism == null) {
			throw new NullPointerException("Session digestInit parameter was null.");
		}
		
		// check mechanism 
		if ( !mechanism.isDigestMechanism()) {
			throw new IllegalArgumentException("Session digestInit mechanism " + mechanism.getMechanismCode() + " invalid.");
		}
		
		byte[] userDef = null;
		if ( mechanism.getMechanismCode() ==  SMechanism.SKM_SM3_USER_ID ) {
			if (  userID == null )
				userDef = "1234567812345678".getBytes();
			else 
				userDef = userID;

			return hsmImplAPI.IMPL_HashInit(mechanism.getMechanismCode(), sm2PublKey, userID, userID.length);
		} else {
			return hsmImplAPI.IMPL_HashInit(mechanism.getMechanismCode(), null, null, 0);			
		}
	}

	@Override
	public SDigest digestUpdate(SDigest digest, byte[] input) throws Exception {
		if (input == null) {
			throw new NullPointerException("Session digestUpdate parameter was null.");
		}
		
		return hsmImplAPI.IMPL_HashUpdate(digest, input);
	}

	@Override
	public byte[] digestFinal(SDigest digest) throws Exception {
		return hsmImplAPI.IMPL_HashFinal(digest);
	}

	@Override
	public byte[] digest(SMechanism mechanism, byte[] input) throws Exception {
		SDigest dist = null;
		dist = digestInit(mechanism, null, null);
		dist = digestUpdate(dist, input);
		return digestFinal(dist);
	}

}
