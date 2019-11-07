package cn.com.flat.head.service.impl;

import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Jdbc;
import cn.com.flat.head.service.ConfigService;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class configServiceImpl implements ConfigService {
    public static final String  PREFIX="jdbc.";

    @Override
    public BooleanCarrier  updateJdbcConfig(Jdbc jdbc) {

        Properties prop = new Properties();
        BooleanCarrier booleanCarrier = new  BooleanCarrier();
        try{
         Resource resource = new FileUrlResource("config/application.properties");
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
            resource = new FileUrlResource("config/application.properties");
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
}
