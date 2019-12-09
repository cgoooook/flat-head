package cn.com.flat.head.integration;

import cn.com.flat.head.cache.ClientCache;
import cn.com.flat.head.pojo.AccessToken;
import cn.com.flat.head.rest.annotation.FlatRestService;
import cn.com.flat.head.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by panzhuowen on 2019/12/8.
 */
@FlatRestService
@Path("/api")
public class TokenRest {

    @Autowired
    private ClientCache clientCache;

    @Autowired
    private TokenService tokenService;

    @POST
    @Path("/access")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> access(@FormParam("cid") String cid) {
        Map<String, Object> ret = new HashMap<>();
        AccessToken accessToken = clientCache.getAccessToken(cid);
        if (accessToken == null) {
            ret.put("retcode", 302);
            ret.put("success", false);
            ret.put("message", "can't find client");
            return ret;
        }
        try{
            tokenService.generateToken(accessToken);
        }catch(Exception e)
        {
            ret.put("retcode", 500);
            ret.put("success", false);
            ret.put("message", e.toString());
            return ret;
        }
        ret.put("retcode", 0);
        ret.put("success", true);
        ret.put("message", accessToken.getRData());
        return ret;
    }

}
