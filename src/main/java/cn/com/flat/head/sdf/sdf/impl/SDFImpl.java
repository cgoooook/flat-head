package cn.com.flat.head.sdf.sdf.impl;

import cn.com.flat.head.sdf.cipher.SDigest;
import cn.com.flat.head.sdf.cipher.SKey;
import cn.com.flat.head.sdf.cipher.SKeyPair;
import cn.com.flat.head.sdf.cipher.SMechanism;
import cn.com.flat.head.sdf.sdf.SDFConstants;
import cn.com.flat.head.sdf.sdf.SDFException;
import cn.com.flat.head.sdf.sdf.impl.jna.JNASDFApi;
import cn.com.flat.head.sdf.util.Arrays;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;


public class SDFImpl {

    private Pointer pDevHandle = null;
    private SDFConfigure hsmConfig = null;

    public synchronized void Impl_init() throws SDFException {
        int rv = 0;

        if (hsmConfig == null) {
            hsmConfig = new SDFConfigure();
            new JNASDFApi();
        }

        if (pDevHandle != null)
            return;

        PointerByReference ppDevHandle = new PointerByReference(Pointer.NULL);

        rv = JNASDFApi.CLib.INSTANCE.SDF_OpenDevice(ppDevHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        pDevHandle = ppDevHandle.getValue();


    }

    public synchronized void Impl_final() throws SDFException {
        int rv = 0;

        if (pDevHandle == null)
            return;

        rv = JNASDFApi.CLib.INSTANCE.SDF_CloseDevice(pDevHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        pDevHandle = null;
    }

    private Pointer openSession() throws SDFException {
        int rv;
        PointerByReference ppSessionHandle = new PointerByReference(Pointer.NULL);

        rv = JNASDFApi.CLib.INSTANCE.SDF_OpenSession(pDevHandle, ppSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        return ppSessionHandle.getValue();
    }

    private void closeSession(Pointer pSessionHandle) throws SDFException {
        int rv;
        rv = JNASDFApi.CLib.INSTANCE.SDF_CloseSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }
    }

    public byte[] IMPL_GenerateRandom(int rndLength) throws SDFException {
        int rv = 0;
        byte[] random = null;

        Pointer pSessionHandle = openSession();

        if (hsmConfig.getRandomLengthMaxConfig() < rndLength || 1 > rndLength) {
            throw new IllegalArgumentException(
                    "Random cannot be greater than " + hsmConfig.getRandomLengthMaxConfig() + " or less than 1.");
        }

        random = new byte[rndLength];
        rv = JNASDFApi.CLib.INSTANCE.SDF_GenerateRandom(pSessionHandle, rndLength, random);
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        return random;
    }

    public SKeyPair IMPL_GenerateKeyPair_SM2(int uiKeyBits) throws SDFException {
        int rv = 0;
        SKeyPair sm2KeyPair = null;
        JNASDFApi.ECCrefPublicKey.ByReference pPubKey = new JNASDFApi.ECCrefPublicKey.ByReference();
        JNASDFApi.ECCrefPrivateKey.ByReference pPriKey = new JNASDFApi.ECCrefPrivateKey.ByReference();

        Pointer pSessionHandle = openSession();

        rv = JNASDFApi.CLib.INSTANCE.SDF_GenerateKeyPair_ECC(pSessionHandle, SDFConstants.SM2_KEY, pPubKey, pPriKey);
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        sm2KeyPair = new SKeyPair(sm2PublJna2SKey(pPubKey), sm2PrivJna2SKey(pPriKey));

        return sm2KeyPair;
    }

    public SKey IMPL_ExportSignPublicKey_SM2(int iKeyIndex) throws SDFException {
        int rv = 0;

        if (!isSM2IndexAbled(iKeyIndex)) {
            throw new IllegalArgumentException("SM2 key index than " + hsmConfig.getSM2IndexMaxConfig() + " or less than 1.");
        }

        JNASDFApi.ECCrefPublicKey.ByReference pPubKey = new JNASDFApi.ECCrefPublicKey.ByReference();
        Pointer pSessionHandle = openSession();

        rv = JNASDFApi.CLib.INSTANCE.SDF_ExportSignPublicKey_ECC(pSessionHandle, iKeyIndex, pPubKey);
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        return sm2PublJna2SKey(pPubKey);
    }

    public SKey IMPL_ExportEncPublicKey_SM2(int iKeyIndex) throws SDFException {
        int rv = 0;

        if (!isSM2IndexAbled(iKeyIndex)) {
            throw new IllegalArgumentException("SM2 key index than " + hsmConfig.getSM2IndexMaxConfig() + " or less than 1.");
        }

        JNASDFApi.ECCrefPublicKey.ByReference pPubKey = new JNASDFApi.ECCrefPublicKey.ByReference();
        Pointer pSessionHandle = openSession();

        rv = JNASDFApi.CLib.INSTANCE.SDF_ExportEncPublicKey_ECC(pSessionHandle, iKeyIndex, pPubKey);
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        return sm2PublJna2SKey(pPubKey);
    }

    public byte[] IMPL_SM2Sign(SKey sm2Priv, byte[] pInput) throws SDFException {
        int rv = 0;

        JNASDFApi.ECCSignature.ByReference pSignature = new JNASDFApi.ECCSignature.ByReference();
        Pointer pSessionHandle = openSession();

        if (!isSM2SignDataLengthAbled(pInput.length)) {
            throw new IllegalArgumentException("SM2 Sign input length than " + hsmConfig.getSM2SignLengthMaxConfig() + " or less than " + hsmConfig.getSM2SignLengthMinConfig());
        }

        if (isSM2IndexAbled(sm2Priv.getKeyID())) {
            rv = JNASDFApi.CLib.INSTANCE.SDF_InternalSign_ECC(pSessionHandle, sm2Priv.getKeyID(), pInput, pInput.length, pSignature);
        } else { // need add
            JNASDFApi.ECCrefPrivateKey.ByReference pPriv = sm2PrivSKey2Jna(sm2Priv);
            rv = JNASDFApi.CLib.INSTANCE.SDF_ExternalSign_ECC_Ex(pSessionHandle, SMechanism.SKM_SM2_SIGN, pPriv, pInput, pInput.length, pSignature);
        }
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        return sm2SignJna2Data(pSignature);
    }

    public boolean IMPL_SM2Verify(SKey sm2Publ, byte[] pInput, byte[] signature) throws SDFException {
        int rv = 0;
        boolean bln = false;

        if (!isSM2SignDataLengthAbled(pInput.length)) {
            throw new IllegalArgumentException("SM2 verify input length than " + hsmConfig.getSM2SignLengthMaxConfig() + " or less than " + hsmConfig.getSM2SignLengthMinConfig());
        }

        JNASDFApi.ECCSignature.ByReference pSignature = sm2SignData2Jna(signature);
        Pointer pSessionHandle = openSession();

        if (isSM2IndexAbled(sm2Publ.getKeyID())) {
            rv = JNASDFApi.CLib.INSTANCE.SDF_InternalVerify_ECC(pSessionHandle, sm2Publ.getKeyID(), pInput, pInput.length, pSignature);
        } else {
            JNASDFApi.ECCrefPublicKey.ByReference pPubl = sm2PublSKey2Jna(sm2Publ);
            rv = JNASDFApi.CLib.INSTANCE.SDF_ExternalVerify_ECC(pSessionHandle, pPubl, pInput, pInput.length, pSignature);
        }
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            bln = false;
        }

        bln = true;

        return bln;
    }

    public byte[] IMPL_SM2Encrypt(SKey sm2Publ, byte[] pInput) throws SDFException {
        int rv = 0;

        JNASDFApi.ECCCipher.ByReference pEncrypted = new JNASDFApi.ECCCipher.ByReference();
        Pointer pSessionHandle = openSession();

        if (pInput.length > hsmConfig.getSM2EncryptLengthMaxConfig()) {
            throw new IllegalArgumentException("SM2 encrypt input length than " + hsmConfig.getSM2SignLengthMaxConfig() + " or less than 1.");
        }

        // need modify
        if (isSM2IndexAbled(sm2Publ.getKeyID())) {
            rv = JNASDFApi.CLib.INSTANCE.SDF_InternalEncrypt_ECC(pSessionHandle, SDFConstants.SM2_KEY, sm2Publ.getKeyID(), pInput, pInput.length, pEncrypted);
        } else {
            JNASDFApi.ECCrefPublicKey.ByReference pPubl = sm2PublSKey2Jna(sm2Publ);
            rv = JNASDFApi.CLib.INSTANCE.SDF_ExternalEncrypt_ECC(pSessionHandle, SDFConstants.SM2_KEY, pPubl, pInput, pInput.length, pEncrypted);
        }
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        return sm2EncryptJna2Data(pEncrypted);
    }

    public byte[] IMPL_SM2Decrypt(SKey sm2Priv, byte[] pInput) throws SDFException {
        int rv = 0;
        byte[] decrypt = null;
        int iKeyIndex;

        if (pInput.length > hsmConfig.getSM2EncryptLengthMaxConfig()) {
            throw new IllegalArgumentException("SM2 decrypt input length than " + hsmConfig.getSM2SignLengthMaxConfig() + " or less than 1.");
        }

        JNASDFApi.ECCCipher.ByReference pEncrypted = sm2EncryptData2Jna(pInput);
        Pointer pSessionHandle = openSession();

        JNASDFApi.ECCrefPrivateKey.ByReference pPriv;

        if (isSM2IndexAbled(sm2Priv.getKeyID())) {
            iKeyIndex = sm2Priv.getKeyID();
            pPriv = null;
        } else {
            pPriv = sm2PrivSKey2Jna(sm2Priv);
            iKeyIndex = 0;
        }

        byte[] pOutput = new byte[hsmConfig.getSM2EncryptLengthMaxConfig()];
        IntByReference pOutputLength = new IntByReference();
        rv = JNASDFApi.CLib.INSTANCE.SDF_E_Decrypt_ECC(pSessionHandle, SMechanism.SKM_SM2_ENC, iKeyIndex, pPriv, pEncrypted, pOutput, pOutputLength);
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        int decLength = pOutputLength.getValue();
        decrypt = new byte[decLength];
        System.arraycopy(pOutput, 0, decrypt, 0, decLength);

        return decrypt;
    }

    public byte[] IMPL_Encrypt(int iAlgID, SKey key, byte[] iv, byte[] pInput) throws SDFException {
        int rv;
        byte[] encrypt = null;
        if (!isInputLengthAbled(pInput.length)) {
            throw new IllegalArgumentException("Key encrypt input length than " + hsmConfig.getInputLengthMaxConfig() + " or less than 1.");
        }

        if (!isInputLengthBlockAbled(key.getKeyType(), pInput.length)) {
            throw new IllegalArgumentException("encrypt input length block invalid. ");
        }

        Pointer pSessionHandle = openSession();
        Pointer pKeyHandle = null;
        if (isKeyIndexAbled(key.getKeyID())) {
            pKeyHandle = Pointer.createConstant(Integer.SIZE / 8);
            pKeyHandle.setInt(0, key.getKeyID());
        } else {
            PointerByReference ppKeyHandle = new PointerByReference(Pointer.NULL);
            rv = JNASDFApi.CLib.INSTANCE.SDF_ImportKey(pSessionHandle, key.getKey(), key.getKey().length, ppKeyHandle);
            if (rv != SDFConstants.SDR_OK) {
                closeSession(pSessionHandle);
                throw new SDFException(rv);
            }
            pKeyHandle = ppKeyHandle.getValue();
        }

        IntByReference pOutputLength = new IntByReference();

        encrypt = new byte[pInput.length];

        rv = JNASDFApi.CLib.INSTANCE.SDF_Encrypt(pSessionHandle, pKeyHandle, iAlgID, iv, pInput, pInput.length, encrypt, pOutputLength);
        JNASDFApi.CLib.INSTANCE.SDF_DestroyKey(pSessionHandle, pKeyHandle);
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        return encrypt;
    }

    public byte[] IMPL_Decrypt(int iAlgID, SKey key, byte[] iv, byte[] pInput) throws SDFException {
        int rv;
        byte[] decrypt = null;

        if (!isInputLengthAbled(pInput.length)) {
            throw new IllegalArgumentException("Key encrypt input length than " + hsmConfig.getInputLengthMaxConfig() + " or less than 1.");
        }

        if (isInputLengthBlockAbled(key.getKeyType(), pInput.length)) {
            throw new IllegalArgumentException("encrypt input length block invalid. ");
        }

        Pointer pSessionHandle = openSession();
        Pointer pKeyHandle = null;
        if (isKeyIndexAbled(key.getKeyID())) {
            pKeyHandle = Pointer.createConstant(Integer.SIZE / 8);
            pKeyHandle.setInt(0, key.getKeyID());
        } else {
            PointerByReference ppKeyHandle = new PointerByReference(Pointer.NULL);
            rv = JNASDFApi.CLib.INSTANCE.SDF_ImportKey(pSessionHandle, key.getKey(), key.getKey().length, ppKeyHandle);
            if (rv != SDFConstants.SDR_OK) {
                closeSession(pSessionHandle);
                throw new SDFException(rv);
            }
            pKeyHandle = ppKeyHandle.getValue();
        }

        IntByReference pOutputLength = new IntByReference();

        decrypt = new byte[pInput.length];

        rv = JNASDFApi.CLib.INSTANCE.SDF_Decrypt(pSessionHandle, pKeyHandle, iAlgID, iv, pInput, pInput.length, decrypt, pOutputLength);
        JNASDFApi.CLib.INSTANCE.SDF_DestroyKey(pSessionHandle, pKeyHandle);
        closeSession(pSessionHandle);
        if (rv != SDFConstants.SDR_OK) {
            throw new SDFException(rv);
        }

        return decrypt;
    }

    public SDigest IMPL_HashInit(int iAlgID, SKey sm2Publ, byte[] pUserID, int userIDLength) throws SDFException {
        int rv;

        Pointer pSessionHandle = openSession();
        JNASDFApi.ECCrefPublicKey.ByReference pPublicKey = null;

        if (iAlgID == SMechanism.SKM_SM3_USER_ID && (sm2Publ == null || sm2Publ.getKey() == null)) {
            throw new IllegalArgumentException("Impl hash mechanism SKM_SM3_USER_ID invalid, parameter SM2 public key was null.");
        }

        if (iAlgID == SMechanism.SKM_SM3_USER_ID) {
            pPublicKey = sm2PublSKey2Jna(sm2Publ);
        }

        rv = JNASDFApi.CLib.INSTANCE.SDF_HashInit(pSessionHandle, iAlgID, pPublicKey, pUserID, userIDLength);
        if (rv != SDFConstants.SDR_OK) {
            closeSession(pSessionHandle);
            throw new SDFException(rv);
        }

        return new SDigest(pSessionHandle);
    }

    public SDigest IMPL_HashUpdate(SDigest digest, byte[] pInput) throws SDFException {
        int rv;

        if (digest == null || digest.getSessionHandle() == null) {
            throw new IllegalArgumentException("Impl Digest update  parameter digest unInit.");
        }

        if (!isInputLengthAbled(pInput.length)) {
            throw new IllegalArgumentException("Digest update input length than " + hsmConfig.getInputLengthMaxConfig() + " or less than 1.");
        }

        rv = JNASDFApi.CLib.INSTANCE.SDF_HashUpdate(digest.getSessionHandle(), pInput, pInput.length);
        if (rv != SDFConstants.SDR_OK) {
            closeSession(digest.getSessionHandle());
            throw new SDFException(rv);
        }

        return digest;
    }

    public byte[] IMPL_HashFinal(SDigest digest) throws SDFException {
        int rv;

        if (digest == null || digest.getSessionHandle() == null) {
            closeSession(digest.getSessionHandle());
            throw new IllegalArgumentException("Impl Digest final  parameter digest unInit.");
        }

        byte[] pDigest = new byte[128];
        IntByReference pDigestLength = new IntByReference();

        rv = JNASDFApi.CLib.INSTANCE.SDF_HashFinal(digest.getSessionHandle(), pDigest, pDigestLength);
        closeSession(digest.getSessionHandle());

        byte[] dst = new byte[pDigestLength.getValue()];

        System.arraycopy(pDigest, 0, dst, 0, pDigestLength.getValue());

        return dst;
    }

    private SKey sm2PublJna2SKey(JNASDFApi.ECCrefPublicKey.ByReference pPubKey) {
        byte[] oid = {48, 89, 48, 19, 6, 7, 42, -122, 72, -50, 61, 2, 1, 6, 8, 42, -127, 28, -127, 69, 1, -126, 45, 3, 66, 0, 4};

        if (pPubKey == null)
            throw new NullPointerException("JNA SM2 Public Key was null.");

        if (pPubKey.bits != SDFConstants.SM2_KEY_BITES) {
            throw new IllegalArgumentException("JNA SM2 Public Key was invalid.");
        }

        int sm2Length = (pPubKey.bits + 7) / 8;
        byte[] keyBuf = new byte[2 * sm2Length + oid.length];

        int offset = 0;
        // copy oid header
        System.arraycopy(oid, 0, keyBuf, offset, oid.length);
        offset += oid.length;
        // copy sm2 public key x
        System.arraycopy(pPubKey.x, SDFConstants.ECCref_MAX_LEN - sm2Length, keyBuf, offset, sm2Length);
        offset += sm2Length;
        // copy sm2 public key y
        System.arraycopy(pPubKey.y, SDFConstants.ECCref_MAX_LEN - sm2Length, keyBuf, offset, sm2Length);

        SKey sm2PubKey = SKey.get(SKey.SKK_SM2_PUBL_KEY);
        sm2PubKey.setKey(keyBuf);

        return sm2PubKey;
    }

    private SKey sm2PrivJna2SKey(JNASDFApi.ECCrefPrivateKey.ByReference pPriKey) {
        byte[] oid = {48, 65, 2, 1, 0, 48, 19, 6, 7, 42, -122, 72, -50, 61, 2, 1, 6, 8, 42, -127, 28, -127, 69, 1, -126, 45, 4, 39, 48, 37, 2, 1, 1, 4, 32};

        if (pPriKey == null)
            throw new NullPointerException("JNA SM2 Private Key was null.");

        if (pPriKey.bits != SDFConstants.SM2_KEY_BITES) {
            throw new IllegalArgumentException("JNA SM2 Private Key Bites was invalid.");
        }

        int sm2Length = (pPriKey.bits + 7) / 8;
        byte[] keyBuf = new byte[sm2Length + oid.length];

        int offset = 0;
        // copy oid header
        System.arraycopy(oid, 0, keyBuf, offset, oid.length);
        offset += oid.length;
        // copy sm2 private key d
        System.arraycopy(pPriKey.d, SDFConstants.ECCref_MAX_LEN - sm2Length, keyBuf, offset, sm2Length);

        SKey sm2PriKey = SKey.get(SKey.SKK_SM2_PRIV_KEY);
        sm2PriKey.setKey(keyBuf);

        return sm2PriKey;
    }

    private JNASDFApi.ECCrefPublicKey.ByReference sm2PublSKey2Jna(SKey pubKey) {
        byte[] oid = {48, 89, 48, 19, 6, 7, 42, -122, 72, -50, 61, 2, 1, 6, 8, 42, -127, 28, -127, 69, 1, -126, 45, 3, 66, 0, 4};


        if (pubKey == null)
            throw new NullPointerException("SM2 Public Key was null.");

        if (pubKey.getKeyType() != SKey.SKK_SM2_PUBL_KEY)
            throw new NullPointerException("SM2 Public Key type invalid.");

        int sm2Length = (SDFConstants.SM2_KEY_BITES + 7) / 8;

        byte[] keyBuf = pubKey.getKey();

        if (keyBuf == null || keyBuf.length != (oid.length + 2 * sm2Length))
            throw new IllegalArgumentException("SM2 Public Key was Invalid.");

        if (Arrays.areEqual(keyBuf, oid, oid.length)) {
            throw new IllegalArgumentException("SM2 Public Key OID was Invalid.");
        }

        JNASDFApi.ECCrefPublicKey.ByReference pJnaPublKey = new JNASDFApi.ECCrefPublicKey.ByReference();
        pJnaPublKey.bits = SDFConstants.SM2_KEY_BITES;

        // copy sm2 public key x
        int offset = oid.length;
        System.arraycopy(keyBuf, offset, pJnaPublKey.x, SDFConstants.ECCref_MAX_LEN - sm2Length, sm2Length);

        // copy sm2 public key y
        offset += sm2Length;
        System.arraycopy(keyBuf, offset, pJnaPublKey.y, SDFConstants.ECCref_MAX_LEN - sm2Length, sm2Length);

        return pJnaPublKey;
    }

    private JNASDFApi.ECCrefPrivateKey.ByReference sm2PrivSKey2Jna(SKey priKey) {
        byte[] oid = {48, 65, 2, 1, 0, 48, 19, 6, 7, 42, -122, 72, -50, 61, 2, 1, 6, 8, 42, -127, 28, -127, 69, 1, -126, 45, 4, 39, 48, 37, 2, 1, 1, 4, 32};
        if (priKey == null)
            throw new NullPointerException("SM2 Private Key was null.");

        if (priKey.getKeyType() != SKey.SKK_SM2_PRIV_KEY)
            throw new NullPointerException("SM2 Private Key type invalid.");

        int sm2Length = (SDFConstants.SM2_KEY_BITES + 7) / 8;

        byte[] keyBuf = priKey.getKey();
        if (keyBuf == null || keyBuf.length != (oid.length + sm2Length))
            throw new IllegalArgumentException("SM2 Private Key was Invalid.");

        if (Arrays.areEqual(keyBuf, oid, oid.length)) {
            throw new IllegalArgumentException("SM2 Private Key OID was Invalid.");
        }

        JNASDFApi.ECCrefPrivateKey.ByReference pJnaPrivKey = new JNASDFApi.ECCrefPrivateKey.ByReference();
        pJnaPrivKey.bits = SDFConstants.SM2_KEY_BITES;

        int offset = oid.length;
        System.arraycopy(keyBuf, offset, pJnaPrivKey.d, SDFConstants.ECCref_MAX_LEN - sm2Length, sm2Length);

        return pJnaPrivKey;
    }

    private JNASDFApi.ECCSignature.ByReference sm2SignData2Jna(byte[] signature) {

        if (signature == null)
            throw new NullPointerException("SM2 signature was null.");

        int sm2Length = (SDFConstants.SM2_KEY_BITES + 7) / 8;

        if (signature.length != 2 * sm2Length) {
            throw new IllegalArgumentException("SM2 Sign value was Invalid.");
        }

        JNASDFApi.ECCSignature.ByReference pSM2Sign = new JNASDFApi.ECCSignature.ByReference();

        int offset = 0;
        System.arraycopy(signature, offset, pSM2Sign.r, SDFConstants.ECCref_MAX_LEN - sm2Length, sm2Length);
        offset += sm2Length;
        System.arraycopy(signature, offset, pSM2Sign.s, SDFConstants.ECCref_MAX_LEN - sm2Length, sm2Length);

        return pSM2Sign;
    }

    private byte[] sm2SignJna2Data(JNASDFApi.ECCSignature.ByReference pSignature) {

        if (pSignature == null || pSignature.r == null || pSignature.s == null)
            throw new NullPointerException("JNA SM2 signature was null.");

        int sm2Length = (SDFConstants.SM2_KEY_BITES + 7) / 8;

        byte[] signature = new byte[2 * sm2Length];


        int offset = 0;
        System.arraycopy(pSignature.r, SDFConstants.ECCref_MAX_LEN - sm2Length, signature, offset, sm2Length);
        offset += sm2Length;
        System.arraycopy(pSignature.s, SDFConstants.ECCref_MAX_LEN - sm2Length, signature, offset, sm2Length);

        return signature;
    }

    private byte[] sm2EncryptJna2Data(JNASDFApi.ECCCipher.ByReference pEncrypted) {
        if (pEncrypted == null)
            throw new NullPointerException("JNA SM2 encrypted was null.");

        int sm2Length = (SDFConstants.SM2_KEY_BITES + 7) / 8;

        int l = pEncrypted.l;

        if (l < 1 || l > hsmConfig.getSM2EncryptLengthMaxConfig()) {
            throw new IllegalArgumentException("SM2 JNA Encrypt value length was Invalid.");
        }

        byte[] encrypted = new byte[3 * sm2Length + l];
        // cipher x
        int offset = 0;
        System.arraycopy(pEncrypted.x, SDFConstants.ECCref_MAX_LEN - sm2Length, encrypted, offset, sm2Length);
        // cipher y
        offset += sm2Length;
        System.arraycopy(pEncrypted.y, SDFConstants.ECCref_MAX_LEN - sm2Length, encrypted, offset, sm2Length);
        // cipher m
        offset += sm2Length;
        System.arraycopy(pEncrypted.m, 0, encrypted, offset, sm2Length);
        // cipher c
        offset += sm2Length;
        System.arraycopy(pEncrypted.c, 0, encrypted, offset, l);

        return encrypted;
    }

    private JNASDFApi.ECCCipher.ByReference sm2EncryptData2Jna(byte[] encrypted) {
        if (encrypted == null)
            throw new NullPointerException("SM2 encrypted was null.");

        int sm2Length = (SDFConstants.SM2_KEY_BITES + 7) / 8;

        if (encrypted.length <= (3 * sm2Length) || encrypted.length > (3 * sm2Length) + hsmConfig.getSM2EncryptLengthMaxConfig()) {
            throw new IllegalArgumentException("SM2 Encrypt value length was Invalid.");
        }

        JNASDFApi.ECCCipher.ByReference jnaCipher = new JNASDFApi.ECCCipher.ByReference();
        // cipher x
        int offset = 0;
        System.arraycopy(encrypted, offset, jnaCipher.x, SDFConstants.ECCref_MAX_LEN - sm2Length, sm2Length);
        // ciper y
        offset += sm2Length;
        System.arraycopy(encrypted, offset, jnaCipher.y, SDFConstants.ECCref_MAX_LEN - sm2Length, sm2Length);
        // cipher m
        offset += sm2Length;
        System.arraycopy(encrypted, offset, jnaCipher.m, 0, sm2Length);
        // cipher l
        jnaCipher.l = encrypted.length - (3 * sm2Length);
        // cipher c
        offset += sm2Length;
        System.arraycopy(encrypted, offset, jnaCipher.c, 0, jnaCipher.l);

        return jnaCipher;
    }

    private boolean isSM2IndexAbled(int iKeyIndex) {
        if (hsmConfig.getSM2IndexMaxConfig() < iKeyIndex || 1 > iKeyIndex) {
            return false;
        }
        return true;
    }

    private boolean isSM2SignDataLengthAbled(int length) {
        if (length > hsmConfig.getSM2SignLengthMaxConfig() || length < hsmConfig.getSM2SignLengthMinConfig()) {
            return false;
        }
        return true;
    }

    private boolean isInputLengthAbled(int inLen) {
        if (inLen > hsmConfig.getInputLengthMaxConfig()) {
            return false;
        }
        return true;
    }

    private boolean isInputLengthBlockAbled(int keytype, int length) {
        if (keytype == SKey.SKK_SM1_KEY || keytype == SKey.SKK_SM4_KEY) {
            if (0 == length % 16) {
                return true;
            }
        }

        return false;
    }

    private boolean isKeyIndexAbled(int iKeyIndex) {
        if (hsmConfig.getKEYIndexMaxConfig() < iKeyIndex || 1 > iKeyIndex) {
            return false;
        }
        return true;
    }
}
