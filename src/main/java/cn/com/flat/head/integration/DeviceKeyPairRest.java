package cn.com.flat.head.integration;

import cn.com.flat.head.pojo.BooleanCarrier;
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
@Path("/api/keys")
public class DeviceKeyPairRest {

    @Autowired
    private KeyPairApplyService keyPairApplyService;

    @Path("/apply")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map apply(@FormParam("type") String type, @FormParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        try {
            DeviceKeyPair deviceKeyPair = keyPairApplyService.applyKeyPair(type);
            result.put("public", deviceKeyPair.getPubKey());
            result.put("value", deviceKeyPair.getPriKey());
            result.put("success", true);
            result.put("retcode", 0);
            result.put("type", type);
        } catch (Exception e) {
            result.put("success", false);
            result.put("retcode", 500);
            result.put("message", "server error");
        }
        return result;
    }

    @Path("/apply")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map getApply(@FormParam("type") String type, @FormParam("token") String token) {
        return apply(type, token);
    }

    @Path("/bind")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map bind(@FormParam("public") String pubKey, @FormParam("certificate") String cert,
                    @FormParam("serial") String deviceCode, @FormParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        try {
            BooleanCarrier booleanCarrier = keyPairApplyService.bindKey(pubKey, cert, deviceCode);
            if (!booleanCarrier.getResult()) {
                result.put("success", false);
                result.put("retcode", 610);
                result.put("message", booleanCarrier.getMessage());
            } else {
                result.put("success", true);
                result.put("retcode", 0);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("retcode", 500);
            result.put("message", "server error");
        }
        return result;
    }

    @Path("/revoke")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map revoke(@FormParam("public") String pubKey, @FormParam("reason") String reason,@FormParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        try {
            BooleanCarrier booleanCarrier = keyPairApplyService.revokeKey(pubKey, reason);
            if (!booleanCarrier.getResult()) {
                result.put("success", false);
                result.put("retcode", 510);
                result.put("message", booleanCarrier.getMessage());
            } else {
                result.put("success", true);
                result.put("retcode", 0);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("retcode", 500);
            result.put("message", "server error");
        }
        return result;
    }


}
