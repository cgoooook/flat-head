package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.ConfigDao;
import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.exception.KMSException;
import cn.com.flat.head.log.LoggerBuilder;
import cn.com.flat.head.log.OperateType;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.ConfigService;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static final String PREFIX = "jdbc.";
    @Autowired
    ConfigDao configDao;
    private static Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private LogDao logDao;

    @Override
    public BooleanCarrier updateJdbcConfig(Jdbc jdbc) {

        Properties prop = new Properties();
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        boolean result = true;
        try {
            Resource resource = new FileUrlResource("config/jdbc.properties");
            InputStream inputStream = resource.getInputStream();
            prop.load(inputStream);
            Field[] declaredFields = jdbc.getClass().getDeclaredFields();
            OutputStream out = new FileOutputStream(resource.getFile());
            List<String> ups = new ArrayList<>();
            for (Field filed : declaredFields) {
                String filename = filed.getName();
                String method = "get" + filename.substring(0, 1).toUpperCase() + filename.substring(1);
                Object upValue = jdbc.getClass().getMethod(method).invoke(jdbc);
                if (upValue != null && !upValue.equals(prop.getProperty(PREFIX + filename))) {
                    prop.setProperty(PREFIX + filename, upValue.toString());
                    ups.add(PREFIX + filename);
                }
            }
            prop.store(out, "update" + ups.toString());

        } catch (Exception e) {
            booleanCarrier.setResult(false);
            result = false;
            logger.error("updateJdbcConfig error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.jdbcConfig, result, "Error updating database configuration"));
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
            for (Field filed : declaredFields) {
                String filename = PREFIX + filed.getName();
                String property = prop.getProperty(filename);
                String method = "set" + filed.getName().substring(0, 1).toUpperCase() + filed.getName().substring(1);
                Object upValue = jdbc.getClass().getMethod(method, String.class).invoke(jdbc, property);
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
        boolean result = true;

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
            result = false;
            logger.error("editLogLevel error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.logConfig, result, "Error modifying log configuration"));
        }
    }

    @Override
    public void editLogDays(String logDays) {
        boolean result = true;
        try {
            configDao.editLogDays(logDays);
        } catch (Exception e) {
            result = false;
            logger.error("editLogDays error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.logConfig, result, "Error modifying log days configuration"));
        }
    }

    @Override
    public void editUiConfig(MultipartFile file, String copyright) {
        boolean result = true;
        try {
            InputStream ins = file.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = ins.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
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
            result = false;
            logger.error("editUiConfig", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.logConfig, result, "Error modifying UI configuration"));
        }
    }

    @Override
    public SysLogo getUiInfo() {
        LogConfig copyright = configDao.getCopyright();
        SysLogo uiInfo = configDao.getUiInfo();
        if (uiInfo == null) {
            uiInfo = new SysLogo();
            InputStream ins = this.getClass().getClassLoader().getResourceAsStream("static/pages/img/noimage.png");
            byte data[];
            try {
                byte[] buffer = new byte[1024];
                int len = 0;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((len = ins.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }

                bos.flush();
                data = bos.toByteArray();
                uiInfo.setLogo(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        uiInfo.setCopyright(copyright.getConfigValue());
        return uiInfo;
    }

    @Override
    public void sendMail(Mail mail) {
        boolean result = true;
        try{
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
        mailSender.send(simpleMailMessage);}catch (Exception e){
            result = false;
            logger.error("sendMail error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.logConfig, result, "Send mail exception"));
        }
    }

    @Override
    public void saveMail(Mail mail) {
        boolean result = true;
       try{
           configDao.saveMail(mail);
       } catch (Exception e) {
           result = false;
           logger.error("saveMail error", e);
       } finally {
           logDao.addLog(LoggerBuilder.builder(OperateType.logConfig, result, "Save mailbox configuration"));
       }
    }

    @Override
    public Mail getMail() {
        return configDao.getMail();
    }


}
