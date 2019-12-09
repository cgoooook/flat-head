package cn.com.flat.head.cache.impl;

import cn.com.flat.head.cache.ClientCache;
import cn.com.flat.head.pojo.AccessToken;
import cn.com.flat.head.pojo.Menu;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by panzhuowen on 2019/12/8.
 */
@Service
public class ClientCacheImpl implements ClientCache {

    private Map<String, AccessToken> clientMap = new HashMap<>();


    @Override
    public AccessToken getAccessToken(String cid) {
        return clientMap.get(cid);
    }

    @PostConstruct
    public void initClientInfo() throws Exception {
        Resource resource = new FileUrlResource("config/client.properties");
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String cid = (String) enumeration.nextElement();
            AccessToken accessToken = new AccessToken();
            accessToken.setCid(cid);
            accessToken.setKey(properties.getProperty(cid));
            clientMap.put(cid, accessToken);
        }
    }
}
