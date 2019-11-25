package cn.com.flat.head.rest;


import cn.com.flat.head.rest.config.FlatRestSpringConfig;
import cn.com.flat.head.rest.server.FlatRestNettyRestServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass({FlatRestNettyRestServer.class})
@Import({FlatRestSpringConfig.class})
@EnableConfigurationProperties({FlatRestServerProperties.class})
public class FlatRestAutoConfiguration {
}
