package cn.com.flat.head.controller;

import cn.com.flat.head.pojo.CertRequest;
import cn.com.flat.head.pojo.X509Cert;
import cn.com.flat.head.service.CACertService;
import cn.com.flat.head.util.KeyTools;
import cn.com.flat.head.web.AjaxResponse;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.activation.MimetypesFileTypeMap;
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
@RequestMapping("/cacert")
public class CACertController {

    @Autowired
    private CACertService caCertService;

    @RequestMapping("/calist")
    public String calist(HttpServletRequest request) {
        List<X509Cert> caCertList = KeyTools.getCACertList();
        if (caCertList.isEmpty()) {
            request.setAttribute("hasCA", false);
        } else {
            request.setAttribute("hasCA", true);
            request.setAttribute("caList", caCertList);
        }
        return "cert/calist";
    }

    @PostMapping("/generate")
    @ResponseBody
    public AjaxResponse generateCA(CertRequest certRequest) {
        boolean result = caCertService.generateCACert(certRequest);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(result);
        return ajaxResponse;
    }

    @GetMapping("/down")
    public void down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<X509Cert> caCertList = KeyTools.getCACertList();
        if (caCertList.isEmpty()) {
            return;
        }
        X509Cert x509Cert = caCertList.get(0);
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=\"" + x509Cert.getSubject() + ".cer\"");
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(x509Cert.getCertContent()));
        JcaPEMWriter pemWriter = new JcaPEMWriter(new FileWriter(new File("./ca.pem")));
        pemWriter.writeObject(certificate);
        pemWriter.flush();
        pemWriter.close();
        long fileLength = new File("./ca.pem").length();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("./ca.pem"));
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
