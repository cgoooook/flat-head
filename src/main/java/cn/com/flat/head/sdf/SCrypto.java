package cn.com.flat.head.sdf;

import cn.com.flat.head.sdf.sdf.SDFSession;

public class SCrypto {
	public static final String SHSM_LIB = "SHSM_LIB";

	private static volatile Session cryptoSession;
	private static final Object sessionLock = new Object();

	private static String LibName = null;

	private SCrypto() {
	}

	public static Session getSession() throws Exception {

		Session r = cryptoSession;
		if (r == null) {
			synchronized (sessionLock) {
				r = cryptoSession;
				if (r == null) {
					try {
						SDFSession tHardLib = new SDFSession();
						LibName = SHSM_LIB;
						cryptoSession = tHardLib;
						r = cryptoSession;
					} catch (Exception e) {
						throw new Exception("Get device crypto session failed. ", e);
					}
				}
			}
		}
		return r;
	}

	public static String getLibName() {
		return LibName;
	}

}