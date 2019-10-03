package cn.com.flat.head.service;

import cn.com.flat.head.pojo.CertRequest;
import cn.com.flat.head.pojo.X509Cert;

import java.util.List;

/**
 * Created by poney on 2019-10-03.
 */
public interface CACertService {

    List<X509Cert> getCACertList();

    boolean generateCACert(CertRequest request);

    X509Cert getCACert();

}
