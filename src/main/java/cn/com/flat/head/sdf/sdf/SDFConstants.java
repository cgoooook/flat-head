package cn.com.flat.head.sdf.sdf;

public interface SDFConstants {
	public static final boolean TRUE = true;
	public static final boolean FALSE = false;
	
	public static final int BLOCK8  = 8;
	public static final int BLOCK16 = 16;
	

	// Constants of the RSA
	public static final int RSAref_MAX_BITS = 4096;
	public static final int RSAref_MAX_LEN = ((RSAref_MAX_BITS + 7)/8);
	public static final int RSAref_MAX_PBITS = ((RSAref_MAX_BITS + 1) / 2);
	public static final int RSAref_MAX_PLEN = ((RSAref_MAX_PBITS + 7)/ 8);
	
	// Constants of the SM2
	public static final int SM2_KEY = 7;
	public static final int SM2_KEY_BITES = 256;
	

	public static final int ECCref_MAX_BITS = 512;
	public static final int ECCref_MAX_LEN  = ((ECCref_MAX_BITS+7) / 8);
	
	// Return value of the sdf function
	public static final int SDR_OK                         = 0x00000000;            // �����ɹ�
	public static final int SDR_BASE                       = 0x01000000;            // ���������ֵ
	public static final int SDR_UNKNOWERR                  = SDR_BASE + 0x00000001; // δ֪����
	public static final int SDR_NOTSUPPORT                 = SDR_BASE + 0x00000002; // ��֧�ֵĽӿڵ���
	public static final int SDR_COMMFAIL                   = SDR_BASE + 0x00000003; // ���豸ͨ��ʧ��
	public static final int SDR_HARDFAIL                   = SDR_BASE + 0x00000004; // ����ģ������Ӧ
	public static final int SDR_OPENDEVICE                 = SDR_BASE + 0x00000005; // ���豸ʧ��
	public static final int SDR_OPENSESSION                = SDR_BASE + 0x00000006; // �����Ựʧ��
	public static final int SDR_PARDENY                    = SDR_BASE + 0x00000007; // ��˽Կʹ��Ȩ��
	public static final int SDR_KEYNOTEXIST                = SDR_BASE + 0x00000008; // �����ڵ���Կ����
	public static final int SDR_ALGNOTSUPPORT              = SDR_BASE + 0x00000009; // ��֧�ֵ��㷨����
	public static final int SDR_ALGMODNOTSUPPORT           = SDR_BASE + 0x0000000A; // ��֧�ֵ��㷨ģʽ����
	public static final int SDR_PKOPERR                    = SDR_BASE + 0x0000000B; // ��Կ����ʧ��
	public static final int SDR_SKOPERR                    = SDR_BASE + 0x0000000C; // ˽Կ����ʧ��
	public static final int SDR_SIGNERR                    = SDR_BASE + 0x0000000D; // ǩ������ʧ��
	public static final int SDR_VERIFYERR                  = SDR_BASE + 0x0000000E; // ��֤ǩ��ʧ��
	public static final int SDR_SYMOPERR                   = SDR_BASE + 0x0000000F; // �Գ��㷨����ʧ��
	public static final int SDR_STEPERR                    = SDR_BASE + 0x00000010; // �ಽ���㲽�����
	public static final int SDR_FILESIZEERR                = SDR_BASE + 0x00000011; // �ļ����ȳ�������
	public static final int SDR_FILENOEXIST                = SDR_BASE + 0x00000012; // ָ�����ļ�������
	public static final int SDR_FILEOFSERR                 = SDR_BASE + 0x00000013; // �ļ���ʼλ�ô���
	public static final int SDR_KEYTYPEERR                 = SDR_BASE + 0x00000014; // ��Կ���ʹ���
	public static final int SDR_KEYERR                     = SDR_BASE + 0x00000015; // ��Կ����
	public static final int SDR_ENCDATAERR                 = SDR_BASE + 0x00000016; // ECC��Կ����
	public static final int SDR_RANDERR                    = SDR_BASE + 0x00000017; // �������������
	public static final int SDR_PRKRERR                    = SDR_BASE + 0x00000018; // ˽Կʹ��Ȩ�޻�ȡʧ��
	public static final int SDR_MACERR                     = SDR_BASE + 0x00000019; // MAC����ʧ��
	public static final int SDR_FILEEXITS                  = SDR_BASE + 0x0000001A; // ָ�����ļ��Ѿ�����
	public static final int SDR_FILEWRITEERR               = SDR_BASE + 0x0000001B; // �ļ�д����
	public static final int SDR_NUBUFFER                   = SDR_BASE + 0x0000001C; // �洢�ռ䲻��
	public static final int SDR_INARGERR                   = SDR_BASE + 0x0000001D; // �����������
	public static final int SDR_OUTARGERR                  = SDR_BASE + 0x0000001E; // �����������
	
	public static final String NAME_SDR_OK                 = "SDR_OK";
	public static final String NAME_SDR_BASE               = "SDR_BASE";
	public static final String NAME_SDR_UNKNOWERR          = "SDR_UNKNOWERR";
	public static final String NAME_SDR_NOTSUPPORT         = "SDR_NOTSUPPORT";
	public static final String NAME_SDR_COMMFAIL           = "SDR_COMMFAIL";
	public static final String NAME_SDR_HARDFAIL           = "SDR_HARDFAIL";
	public static final String NAME_SDR_OPENDEVICE         = "SDR_OPENDEVICE";
	public static final String NAME_SDR_OPENSESSION        = "SDR_OPENSESSION";
	public static final String NAME_SDR_PARDENY            = "SDR_PARDENY";
	public static final String NAME_SDR_KEYNOTEXIST        = "SDR_KEYNOTEXIST";
	public static final String NAME_SDR_ALGNOTSUPPORT      = "SDR_ALGNOTSUPPORT";
	public static final String NAME_SDR_ALGMODNOTSUPPORT   = "SDR_ALGMODNOTSUPPORT";
	public static final String NAME_SDR_PKOPERR            = "SDR_PKOPERR";
	public static final String NAME_SDR_SKOPERR            = "SDR_SKOPERR";
	public static final String NAME_SDR_SIGNERR            = "SDR_SIGNERR";
	public static final String NAME_SDR_VERIFYERR          = "SDR_VERIFYERR";
	public static final String NAME_SDR_SYMOPERR           = "SDR_SYMOPERR";
	public static final String NAME_SDR_STEPERR            = "SDR_STEPERR";
	public static final String NAME_SDR_FILESIZEERR        = "SDR_FILESIZEERR";
	public static final String NAME_SDR_FILENOEXIST        = "SDR_FILENOEXIST";
	public static final String NAME_SDR_FILEOFSERR         = "SDR_FILEOFSERR";
	public static final String NAME_SDR_KEYTYPEERR         = "SDR_KEYTYPEERR";
	public static final String NAME_SDR_KEYERR             = "SDR_KEYERR";
	public static final String NAME_SDR_ENCDATAERR         = "SDR_ENCDATAERR";
	public static final String NAME_SDR_RANDERR            = "SDR_RANDERR";
	public static final String NAME_SDR_PRKRERR            = "SDR_PRKRERR";
	public static final String NAME_SDR_MACERR             = "SDR_MACERR";
	public static final String NAME_SDR_FILEEXITS          = "SDR_FILEEXITS";
	public static final String NAME_SDR_FILEWRITEERR       = "SDR_FILEWRITEERR";
	public static final String NAME_SDR_NUBUFFER           = "SDR_NUBUFFER";
	public static final String NAME_SDR_INARGERR           = "SDR_INARGERR";
	public static final String NAME_SDR_OUTARGERR          = "SDR_OUTARGERR";
}

























