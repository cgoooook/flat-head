package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.ConfigDao;
import cn.com.flat.head.exception.KMSException;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.ConfigService;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class ConfigServiceImpl implements ConfigService {
    public static final String  PREFIX="jdbc.";
    @Autowired
    ConfigDao configDao;

    @Override
    public BooleanCarrier  updateJdbcConfig(Jdbc jdbc) {

        Properties prop = new Properties();
        BooleanCarrier booleanCarrier = new  BooleanCarrier();
        try{
         Resource resource = new FileUrlResource("config/jdbc.properties");
         InputStream inputStream = resource.getInputStream();
         prop.load(inputStream);
         Field[] declaredFields = jdbc.getClass().getDeclaredFields();
         OutputStream out = new FileOutputStream(resource.getFile());
         List<String> ups = new ArrayList<>();
         for ( Field filed: declaredFields) {
             String filename = filed.getName();
             String method = "get"+filename.substring(0, 1).toUpperCase()+filename.substring(1);
             Object upValue = jdbc.getClass().getMethod(method).invoke(jdbc);
             if(upValue!=null&&!upValue.equals(prop.getProperty(PREFIX+filename))){
                prop.setProperty(PREFIX+filename,upValue.toString());
                ups.add(PREFIX+filename);
            }
         }
         prop.store(out,"update"+ups.toString());

     }catch (IOException e) {

     } catch (IllegalAccessException e) {
         e.printStackTrace();
     } catch (InvocationTargetException e) {
         e.printStackTrace();
     } catch (NoSuchMethodException e) {
         e.printStackTrace();
     }

        booleanCarrier.setResult(true);
        return booleanCarrier;
    }

    @Override
    public Jdbc getJdbcConfig() {
        Properties prop = new Properties();
        Resource resource = null;
        Jdbc jdbc = new Jdbc();
        try {
            resource = new FileUrlResource("config/jdbc.properties");
            InputStream inputStream = resource.getInputStream();
            prop.load(inputStream);
            Field[] declaredFields = jdbc.getClass().getDeclaredFields();
            for (Field filed: declaredFields) {
                String filename = PREFIX+filed.getName();
                String property = prop.getProperty(filename);
                String method = "set"+filed.getName().substring(0, 1).toUpperCase()+filed.getName().substring(1);
                Object upValue = jdbc.getClass().getMethod(method,String.class).invoke(jdbc,property);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return jdbc;
    }

    @Override
    public List<LogConfig> getLogConfig() {

        return configDao.getLogConfig();
    }

    @Override
    public void editLogLevel(String logLevel) {
        try {
            Resource resource = new FileUrlResource("config/application.properties");
            Properties properties = new Properties();
            InputStream inputStream = resource.getInputStream();
            properties.load(inputStream);
            inputStream.close();
            OutputStream out = new FileOutputStream(resource.getFile());
            properties.setProperty("logging.level.cn.com.flat.head", logLevel);
            properties.store(out, "update log level by system");
            configDao.editLogLevel(logLevel);
        } catch (Exception e) {
            throw new KMSException("update log level error", e);
        }
    }

    @Override
    public void editLogDays(String logDays) {
        configDao.editLogDays(logDays);
    }

    @Override
    public void editUiConfig(MultipartFile file, String copyright) {
        try {
            InputStream ins = file.getInputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            while((len=ins.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }
            bos.flush();
            byte data[] = bos.toByteArray();
            configDao.updateCopyright(copyright);
            SysLogo sysLogo = new SysLogo();
            sysLogo.setLogo(data);
            sysLogo.setStatus(1);
            configDao.updateLogo();
            configDao.insertLogo(sysLogo);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public SysLogo getUiInfo() {
        LogConfig copyright = configDao.getCopyright();
        SysLogo uiInfo = configDao.getUiInfo();
        if(uiInfo==null){
            uiInfo= new SysLogo();
            InputStream ins = this.getClass().getClassLoader().getResourceAsStream("static/pages/img/noimage.png");
            byte data[];
            try {
            byte[] buffer=new byte[1024];
            int len=0;
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            while((len=ins.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }

                bos.flush();
                data = bos.toByteArray();
                uiInfo.setLogo(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        uiInfo.setCopyright(copyright.getConfigValue());
        return uiInfo;
    }

    @Override
    public void sendMail(Mail mail) {

        JavaMailSender mailSender = new JavaMailSenderImpl();
        ((JavaMailSenderImpl) mailSender).setHost(mail.getAddr());
        ((JavaMailSenderImpl) mailSender).setPort(mail.getPort());
        ((JavaMailSenderImpl) mailSender).setUsername(mail.getSendMailbox());
        ((JavaMailSenderImpl) mailSender).setPassword(mail.getPassword());
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.timeout", mail.getTimeOut());
        ((JavaMailSenderImpl) mailSender).setJavaMailProperties(javaMailProperties);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mail.getSendMailbox());
        simpleMailMessage.setTo(mail.getReceivingMailbox());
        simpleMailMessage.setSubject("it is a test for spring boot");
        simpleMailMessage.setText("mialSender");
        mailSender.send(simpleMailMessage);
    }

    @Override
    public void saveMail(Mail mail) {
        configDao.saveMail(mail);
    }

    @Override
    public Mail getMail() {
     return    configDao.getMail();
    }


}
