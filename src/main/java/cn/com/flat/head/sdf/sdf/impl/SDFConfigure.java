package cn.com.flat.head.sdf.sdf.impl;

import java.util.Properties;

public class SDFConfigure {
	public static final String NAME_SCNF_SM2_INDEX_MAX     = "SM2_INDEX_MAX";
	public static final String NAME_SCNF_RSA_INDEX_MAX     = "RSA_INDEX_MAX";
	public static final String NAME_SCNF_KEY_INDEX_MAX     = "KEY_INDEX_MAX";
	public static final String NAME_SCNF_RND_LEN_MAX       = "RND_LENGTH_MAX";
	public static final String NAME_SCNF_SM2_SIGN_DATA_MIN = "SM2_SIGN_DATA_MIN";
	public static final String NAME_SCNF_SM2_SIGN_DATA_MAX = "SM2_SIGN_DATA_MAX";
	public static final String NAME_SCNF_SM2_ENC_DATA_MAX  = "SM2_ENC_DATA_MAX";
	public static final String NAME_SCNF_INPUT_LEN_MAX     = "INPUT_LENGTH_MAX";
	public static final String NAME_SCNF_LIB_PATH          = "DLL_PATH";

	protected static final String CONF_HSM_PROPERTIES = "cn/com/hsm/sdf/impl/sdf.properties";
	
	private Properties confHSM_;
	
	private synchronized void InitProperties() {
		if (confHSM_ == null) {
			Properties confHSM = new Properties();
			try {
				confHSM.load(getClass().getClassLoader().getResourceAsStream(CONF_HSM_PROPERTIES));
				confHSM_ = confHSM;
			} catch (Exception exception) {
				System.err.println("Could not read properties for error code names: " + exception.getMessage());
			}
		}
	}
	
	public SDFConfigure() {
		InitProperties();
	}
	
	public int getSM2IndexMaxConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_SM2_INDEX_MAX);
		return Integer.valueOf(value);		
	}
	
	public int getRSAIndexMaxConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_RSA_INDEX_MAX);
		return Integer.valueOf(value);		
	}
	
	public int getKEYIndexMaxConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_KEY_INDEX_MAX);
		return Integer.valueOf(value);		
	}
	
	public int getRandomLengthMaxConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_RND_LEN_MAX);
		return Integer.valueOf(value);		
	}
	public int getSM2SignLengthMinConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_SM2_SIGN_DATA_MIN);
		return Integer.valueOf(value);		
	}
	
	public int getSM2SignLengthMaxConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_SM2_SIGN_DATA_MAX);
		return Integer.valueOf(value);		
	}
	
	public int getSM2EncryptLengthMaxConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_SM2_ENC_DATA_MAX);
		return Integer.valueOf(value);		
	}
	
	public int getInputLengthMaxConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_INPUT_LEN_MAX);
		return Integer.valueOf(value);		
	}		
	
	public String getDLLPathConfig() {
		if ( confHSM_ == null )
			InitProperties();
		
		String value = confHSM_.getProperty(NAME_SCNF_LIB_PATH);
		return value;		
	}		
}
