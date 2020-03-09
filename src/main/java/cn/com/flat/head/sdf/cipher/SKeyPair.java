package cn.com.flat.head.sdf.cipher;

public class SKeyPair {
	private SKey pubKey;
	private SKey prvKey;

	public SKeyPair(SKey pubKey, SKey prvKey) {
		this.pubKey = pubKey;
		this.prvKey = prvKey;
	}
	public SKey getPublicKey() {
		return this.pubKey;
	}

	public SKey getPrivateKey() {
		return this.prvKey;
	}
}
