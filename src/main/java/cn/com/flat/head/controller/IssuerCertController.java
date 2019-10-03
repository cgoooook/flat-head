package cn.com.flat.head.controller;

import cn.com.flat.head.pojo.CertRequest;
import cn.com.flat.head.pojo.X509Cert;
import cn.com.flat.head.service.CACertService;
import cn.com.flat.head.util.KeyTools;
import cn.com.flat.head.web.AjaxResponse;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.List;

/**
 * Created by poney on 2019-10-03.
 */
@Controller
@RequestMapping("/certsign")
public class IssuerCertController {

    @Autowired
    private CACertService caCertService;

    @RequestMapping("/certIussuer")
    public String certIssuer() {
        return "cert/certIssuer";
    }

    @PostMapping("/genTlsCert")
    @ResponseBody
    public AjaxResponse genTlsCert(CertRequest certRequest) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        String filePath = caCertService.generateTlsCert(certRequest);
        if (StringUtils.equalsAnyIgnoreCase("generateError", filePath)) {
            ajaxResponse.setOk(false);
            ajaxResponse.setMessage("产生失败，请检查CA证书");
        } else {
            ajaxResponse.setData(filePath);
            ajaxResponse.setOk(true);
        }
        return ajaxResponse;
    }

    @GetMapping("/tlsDown")
    public void down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String file = request.getParameter("file");
        String fileName = request.getParameter("fileName");
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".p12\"");
        long fileLength = new File("./" + file).length();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("./" + file));
        response.setHeader("Content-Length", fileLength + "");
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }


}
