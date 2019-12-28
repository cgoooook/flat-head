package cn.com.flat.head.integration;

import cn.com.flat.head.log.ApiLogBuilder;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.DeviceKeyPair;
import cn.com.flat.head.rest.annotation.FlatRestService;
import cn.com.flat.head.service.ApiLogService;
import cn.com.flat.head.service.KeyPairApplyService;
import cn.com.flat.head.service.TokenService;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApiLogService apiLogService;

    @Path("/apply")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map apply(@FormParam("type") String type, @FormParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        boolean ret = false;
        String momo = "";
        try {
            String s = tokenService.checkToken(token);
            if (s != null) {
                result.put("success", false);
                result.put("retcode", 400);
                result.put("message", s);
                return result;
            }
            DeviceKeyPair deviceKeyPair = keyPairApplyService.applyKeyPair(type);
            result.put("public", deviceKeyPair.getPubKey());
            result.put("value", deviceKeyPair.getPriKey());
            result.put("success", true);
            result.put("retcode", 0);
            result.put("type", type);
            ret = true;
        } catch (Exception e) {
            result.put("success", false);
            result.put("retcode", 500);
            result.put("message", "server error");
            momo = "server error";
        } finally {
            apiLogService.addApiLog(ApiLogBuilder.builder("/api/keys/apply", ret, momo));
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
                    @FormParam("id") String deviceCode, @FormParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        boolean ret = false;
        String momo = "";
        try {
            String s = tokenService.checkToken(token);
            if (s != null) {
                result.put("success", false);
                result.put("retcode", 400);
                result.put("message", s);
                return result;
            } else {
                BooleanCarrier booleanCarrier = keyPairApplyService.bindKey(pubKey, cert, deviceCode);
                if (!booleanCarrier.getResult()) {
                    result.put("success", false);
                    result.put("retcode", 610);
                    result.put("message", booleanCarrier.getMessage());
                    momo = booleanCarrier.getMessage();
                } else {
                    result.put("success", true);
                    result.put("retcode", 0);
                    ret = true;
                }
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("retcode", 500);
            result.put("message", "server error");
            momo = "server error";
        } finally {
            apiLogService.addApiLog(ApiLogBuilder.builder("/api/keys/bind", ret, momo));
        }
        return result;
    }

    @Path("/revoke")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map revoke(@FormParam("public") String pubKey, @FormParam("reason") String reason,@FormParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        boolean ret = false;
        String momo = "";
        try {
            String s = tokenService.checkToken(token);
            if (s != null) {
                result.put("success", false);
                result.put("retcode", 400);
                result.put("message", s);
            } else {
                BooleanCarrier booleanCarrier = keyPairApplyService.revokeKey(pubKey, reason);
                if (!booleanCarrier.getResult()) {
                    result.put("success", false);
                    result.put("retcode", 510);
                    result.put("message", booleanCarrier.getMessage());
                    momo = booleanCarrier.getMessage();
                } else {
                    result.put("success", true);
                    result.put("retcode", 0);
                    ret = true;
                }
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("retcode", 500);
            result.put("message", "server error");
            momo = "server error";
        } finally {
            apiLogService.addApiLog(ApiLogBuilder.builder("/api/keys/revoke", ret, momo));
        }
        return result;
    }


}
