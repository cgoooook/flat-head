package cn.com.flat.head;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.security.Security;

@SpringBootApplication
@PropertySource(value = {"file:config/bootstrap.properties","file:config/jdbc.properties", "file:config/application.properties"})
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
@ImportResource(value = {"classpath:dal/flat-application-dal.xml", "classpath:dal/shiro-config.xml"})
public class FlatHeadServerApplication {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        SpringApplication application = new SpringApplication(FlatHeadServerApplication.class);
        application.addInitializers((ApplicationContextInitializer) applicationContext -> applicationContext.getBeanFactory().setCacheBeanMetadata(false));
        application.run(args);
    }

}
