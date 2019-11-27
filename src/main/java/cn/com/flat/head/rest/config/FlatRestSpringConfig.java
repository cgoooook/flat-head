package cn.com.flat.head.rest.config;

import cn.com.flat.head.rest.FlatRestServerProperties;
import cn.com.flat.head.rest.filter.FlatCoreFilter;
import cn.com.flat.head.rest.server.FlatRestNettyRestServer;
import cn.com.flat.head.rest.spring.lifecycle.FlatRestDeployedNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("juno-rest-config-for-spring")
public class FlatRestSpringConfig {

    @Autowired
    private FlatRestServerProperties serverProperties;

    @Autowired
    private ApplicationContext applicationContext;


    @ConditionalOnMissingBean(FlatRestNettyRestServer.class)
    @Bean
    public FlatRestNettyRestServer junoRestNettyServer() {
        FlatRestNettyRestServer server = new FlatRestNettyRestServer();
        server.setPort(this.serverProperties.getPort());
        if (this.serverProperties.getAddress() != null) {
            server.setHostName(this.serverProperties.getAddress().getHostAddress());
        }

        return server;
    }

    @Bean
    public FlatRestDeployedNotifier getCubeRestDeployedNotifier() {
        return new FlatRestDeployedNotifier(this.applicationContext, this.junoRestNettyServer());
    }

    @Bean
    public FlatCoreFilter getCubeCoreFilter() {
        return new FlatCoreFilter();
    }

}
