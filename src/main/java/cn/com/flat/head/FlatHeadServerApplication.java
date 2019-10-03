package cn.com.flat.head;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlatHeadServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FlatHeadServerApplication.class);
        application.run(args);
    }

}
