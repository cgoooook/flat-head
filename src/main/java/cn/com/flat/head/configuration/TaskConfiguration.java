package cn.com.flat.head.configuration;

import cn.com.flat.head.util.TaskThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

/**
 * Created by panzhuowen on 2019/12/22.
 */
@Configuration
public class TaskConfiguration {

    @Bean
    public ExecutorService executorService() {
        return TaskThreadPoolExecutor.newFixedThreadPool(35);
    }

}
