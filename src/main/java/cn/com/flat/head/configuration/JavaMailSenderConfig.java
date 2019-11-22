package cn.com.flat.head.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailSenderConfig {
    @Bean
    public JavaMailSender getJavaMail() {
        return new JavaMailSenderImpl();

    }


}
