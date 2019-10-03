package cn.com.flat.head.pojo;

import lombok.Data;

import java.security.PrivateKey;
import java.util.Date;

/**
 * Created by poney on 2019-10-03.
 */
@Data
public class X509Cert {

    private String alias;

    private String certSN;

    private String subject;

    private String issuer;

    private Date before;

    private Date after;

    private String sign;

    private String pubKey;

    private byte[] certContent;

    private PrivateKey privateKey;

}
