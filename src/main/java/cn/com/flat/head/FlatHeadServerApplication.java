package cn.com.flat.head;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
@ImportResource(value = {"classpath:dal/flat-application-dal.xml"})
public class FlatHeadServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FlatHeadServerApplication.class);
        application.addInitializers((ApplicationContextInitializer) applicationContext -> applicationContext.getBeanFactory().setCacheBeanMetadata(false));
        application.run(args);
    }

}
