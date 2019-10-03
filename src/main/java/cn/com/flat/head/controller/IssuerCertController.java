package cn.com.flat.head.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by poney on 2019-10-03.
 */
@Controller
@RequestMapping("/certsign")
public class IssuerCertController {

    @RequestMapping("/certIussuer")
    public String certIssuer() {
        return "cert/certIssuer";
    }

}
