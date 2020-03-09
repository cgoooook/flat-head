package cn.com.flat.head.rest.spring.lifecycle;

import cn.com.flat.head.rest.annotation.FlatRestService;
import cn.com.flat.head.rest.server.FlatRestNettyRestServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;

import javax.ws.rs.ext.Provider;
import java.util.Collection;

public class FlatRestDeployedNotifier implements SmartApplicationListener {



    private FlatRestNettyRestServer cubeRestNettyServer;
    private ApplicationContext applicationContext;

    public FlatRestDeployedNotifier(ApplicationContext applicationContext, FlatRestNettyRestServer cubeRestNettyServer) {
        this.applicationContext = applicationContext;
        this.cubeRestNettyServer = cubeRestNettyServer;
    }

    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ContextRefreshedEvent.class;
    }

    public boolean supportsSourceType(Class<?> sourceType) {
        return ApplicationContext.class.isAssignableFrom(sourceType);
    }

    public void onApplicationEvent(ApplicationEvent event) {
        Collection<Object> service = this.applicationContext.getBeansWithAnnotation(FlatRestService.class).values();
        this.cubeRestNettyServer.addResources(service);
        Collection<Object> providers = this.applicationContext.getBeansWithAnnotation(Provider.class).values();
        this.cubeRestNettyServer.addProviders(providers);
        this.cubeRestNettyServer.start();
    }

    public int getOrder() {
        return 110;
    }
}
