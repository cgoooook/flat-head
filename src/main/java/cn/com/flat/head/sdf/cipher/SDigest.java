package cn.com.flat.head.sdf.cipher;

import com.sun.jna.Pointer;

public class SDigest {
	private Pointer pSessionHandle;
	
	public SDigest(Pointer pSession) {
		this.pSessionHandle = pSession;
	}

	public Pointer getSessionHandle() {
		return pSessionHandle;
	}
}
