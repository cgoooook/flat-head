package cn.com.flat.head.pojo;

import lombok.Data;

/**
 * Created by poney on 2019-10-03.
 */
@Data
public class CertRequest {

    private String subject;

    private String alg;

    private String algLength;

    private String signAlg;

}
