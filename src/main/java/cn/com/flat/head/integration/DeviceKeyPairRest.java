package cn.com.flat.head.integration;

import cn.com.flat.head.pojo.DeviceKeyPair;
import cn.com.flat.head.rest.annotation.FlatRestService;
import cn.com.flat.head.service.KeyPairApplyService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panzhuowen on 2019/11/26.
 */
@FlatRestService
@Path("/api_path/keys")
public class DeviceKeyPairRest {

    @Autowired
    private KeyPairApplyService keyPairApplyService;

    @Path("/apply")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map apply(@QueryParam("type") String type, @QueryParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        try {
            DeviceKeyPair deviceKeyPair = keyPairApplyService.applyKeyPair(type);
            result.put("public", deviceKeyPair.getPubKey());
            result.put("value", deviceKeyPair.getPriKey());
            result.put("success", true);
            result.put("retcode", 0);
            result.put("type", type);
        } catch (Exception e) {

        }
        return result;
    }

    @Path("/apply")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map getApply(@QueryParam("type") String type, @QueryParam("token") String token) {
        return apply(type, token);
    }

}
