package cn.com.flat.head.sdf;

import cn.com.flat.head.sdf.cipher.SDigest;
import cn.com.flat.head.sdf.cipher.SKey;
import cn.com.flat.head.sdf.cipher.SKeyPair;
import cn.com.flat.head.sdf.cipher.SMechanism;

public interface Session {
	
	byte[] sign(SMechanism mechanism, SKey privKey, byte[] input) throws Exception;

	boolean verifySign(SMechanism mechanism, SKey publKey, byte[] input, byte[] signaure) throws Exception;

	byte[] encrypt(SMechanism mechanism, SKey encryptKey, byte[] input) throws Exception;

	byte[] decrypt(SMechanism mechanism, SKey decryptKey, byte[] encrypted)	throws Exception;

	SKey generateKey(SMechanism mechanism, int keyBits) throws Exception;

	SKeyPair generateKeyPair(SMechanism mechanism, int keyBits) throws Exception;

	SKeyPair findKeyPair(SMechanism mechanism, int keyIndex) throws Exception;

	byte[] generateRandom(int randomLength) throws Exception;

	SDigest digestInit(SMechanism mechanism, SKey sm2PublKey, byte[] userID) throws Exception;

	SDigest digestUpdate(SDigest digest, byte[] input) throws Exception;

	byte[] digestFinal(SDigest digest) throws Exception;

	byte[] digest(SMechanism mechanism, byte[] input) throws Exception;
}
