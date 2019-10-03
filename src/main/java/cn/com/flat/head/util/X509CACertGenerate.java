package cn.com.flat.head.util;


import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by poney on 2019-10-03.
 */
public class X509CACertGenerate {

    public static boolean generateCACert(String dn, PrivateKey privateKey, PublicKey publicKey, String signAlg) throws Exception {
        X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                new X500Name(dn),
                new BigInteger(1, genCertSN()),
                new Date(),
                new Date(new Date().getTime() + 1000L * 60 * 60 * 24 * 365 * 10),
                new X500Name(dn),
                SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())
        );
        KeyUsage ku = new KeyUsage(KeyUsage.keyCertSign | KeyUsage.cRLSign);
        certBuilder.addExtension(Extension.keyUsage, false, new DEROctetString(ku));
        certBuilder.addExtension(Extension.basicConstraints, false, ASN1Boolean.TRUE.getEncoded());
        X509CertificateHolder build = certBuilder.build(new JcaContentSignerBuilder(signAlg).build(privateKey));
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(build.getEncoded()));
        keyStore.setKeyEntry(UUID.randomUUID().toString(), privateKey, KeyTools.DEFAULT_PASS.toCharArray(), new Certificate[]{certificate});
        keyStore.store(new FileOutputStream(new File(KeyTools.CA_CERT_PATH)), KeyTools.DEFAULT_PASS.toCharArray());
        return true;
    }

    private static byte[] genCertSN() {
        byte[] sn = new byte[16];
        Random random = new SecureRandom();
        random.nextBytes(sn);
        return sn;
    }

}
