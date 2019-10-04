package cn.com.flat.head.util;


import cn.com.flat.head.pojo.X509Cert;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509ExtensionUtils;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.util.List;
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
        BasicConstraints basicConstraints = new BasicConstraints(true);
        certBuilder.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyCertSign | KeyUsage.cRLSign));
        certBuilder.addExtension(Extension.basicConstraints, false, basicConstraints);
        X509CertificateHolder build = certBuilder.build(new JcaContentSignerBuilder(signAlg).build(privateKey));
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(build.getEncoded()));
        keyStore.setKeyEntry(UUID.randomUUID().toString(), privateKey, KeyTools.DEFAULT_PASS.toCharArray(), new Certificate[]{certificate});
        keyStore.store(new FileOutputStream(new File(KeyTools.CA_CERT_PATH)), KeyTools.DEFAULT_PASS.toCharArray());
        return true;
    }

    public static ByteArrayOutputStream generateTlsCert(String dn, PrivateKey privateKey, PublicKey publicKey, String signAlg, String pass) throws Exception {
        List<X509Cert> caCertList = KeyTools.getCACertList();
        if (caCertList.isEmpty()) {
            return null;
        }
        X509Cert x509Cert = caCertList.get(0);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate rootCert = certificateFactory.generateCertificate(new ByteArrayInputStream(x509Cert.getCertContent()));
        X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                new X500Name(x509Cert.getSubject()),
                new BigInteger(1, genCertSN()),
                new Date(),
                x509Cert.getAfter(),
                new X500Name(dn),
                SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())
        );
        BasicConstraints basicConstraints = new BasicConstraints(false);
        certBuilder.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.digitalSignature));
        certBuilder.addExtension(Extension.basicConstraints, false, basicConstraints);
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(KeyPurposeId.id_kp_clientAuth);
        vector.add(KeyPurposeId.id_kp_serverAuth);
        certBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.37"), false, new DERSequence(vector));
        DigestCalculator calculator = new BcDigestCalculatorProvider().get(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1));
        X509ExtensionUtils extensionUtils = new X509ExtensionUtils(calculator);
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, extensionUtils.createAuthorityKeyIdentifier(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())));
        X509CertificateHolder build = certBuilder.build(new JcaContentSignerBuilder(signAlg).build(x509Cert.getPrivateKey()));
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);
        Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(build.getEncoded()));
        keyStore.setKeyEntry(UUID.randomUUID().toString(), privateKey, pass.toCharArray(), new Certificate[]{certificate});
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        keyStore.store(byteOutputStream, pass.toCharArray());
        return byteOutputStream;
    }


    private static byte[] genCertSN() {
        byte[] sn = new byte[16];
        Random random = new SecureRandom();
        random.nextBytes(sn);
        return sn;
    }

}
