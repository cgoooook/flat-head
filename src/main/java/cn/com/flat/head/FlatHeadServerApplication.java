package cn.com.flat.head;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"classpath*:dal/flat-application-dal.xml"})
public class FlatHeadServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FlatHeadServerApplication.class);
        application.run(args);
    }

}
