package cn.com.flat.head.service.impl;

import cn.com.flat.head.pojo.CertRequest;
import cn.com.flat.head.pojo.X509Cert;
import cn.com.flat.head.service.CACertService;
import cn.com.flat.head.util.X509CACertGenerate;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.List;

/**
 * Created by poney on 2019-10-03.
 */
@Service
public class CACertServiceImpl implements CACertService {
    @Override
    public List<X509Cert> getCACertList() {
        return null;
    }

    @Override
    public boolean generateCACert(CertRequest request) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(request.getAlg());
            keyPairGenerator.initialize(Integer.parseInt(request.getAlgLength()));
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            X509CACertGenerate.generateCACert(request.getSubject(), keyPair.getPrivate(), keyPair.getPublic(), request.getSignAlg());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public X509Cert getCACert() {
        return null;
    }
}
