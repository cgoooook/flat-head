package cn.com.flat.head.integration;

import cn.com.flat.head.rest.annotation.FlatRestService;

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

    @Path("/apply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map apply() {
        Map<String, String> result = new HashMap<>();
        result.put("seuccess", "true");
        return result;
    }

    @Path("/apply")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map getApply() {
        return apply();
    }

}
