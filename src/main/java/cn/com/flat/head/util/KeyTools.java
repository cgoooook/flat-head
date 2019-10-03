package cn.com.flat.head.util;

import cn.com.flat.head.pojo.X509Cert;
import sun.security.x509.X509CertImpl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by poney on 2019-10-03.
 */
public class KeyTools {

    public static final String CA_CERT_PATH = "./ca.keystore";

    public static final String DEFAULT_PASS = "123456";

    public static List<X509Cert> getCACertList() {
        List<X509Cert> certList = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(new File(CA_CERT_PATH))) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(fileInputStream, DEFAULT_PASS.toCharArray());
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                Certificate certificate = keyStore.getCertificate(alias);
                X509Cert x509Cert = parserCert(certificate);
                x509Cert.setAlias(alias);
                x509Cert.setCertContent(certificate.getEncoded());
                x509Cert.setPrivateKey((PrivateKey) keyStore.getKey(alias, DEFAULT_PASS.toCharArray()));
                certList.add(x509Cert);
            }
        } catch (Exception ignore) {

        }
        return certList;
    }


    private static X509Cert parserCert(Certificate certificate) throws Exception {
        X509Cert x509Cert = new X509Cert();
        X509CertImpl cert = (X509CertImpl) certificate;
        x509Cert.setCertSN(cert.getSerialNumber().toString());
        x509Cert.setSubject(cert.getSubjectDN().toString());
        x509Cert.setIssuer(cert.getIssuerDN().toString());
        x509Cert.setAfter(cert.getNotAfter());
        x509Cert.setBefore(cert.getNotBefore());
        return x509Cert;

    }


}
