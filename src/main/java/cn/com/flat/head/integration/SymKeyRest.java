package cn.com.flat.head.integration;

import cn.com.flat.head.log.ApiLogBuilder;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.SymKey;
import cn.com.flat.head.rest.annotation.FlatRestService;
import cn.com.flat.head.service.ApiLogService;
import cn.com.flat.head.service.SymKeyService;
import cn.com.flat.head.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FlatRestService
@Path("/api/skey")
public class SymKeyRest {

    @Autowired
    private SymKeyService symKeyService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApiLogService apiLogService;

    private List<Map> convert(List <SymKey> keys)
    {
        List<Map> results = new ArrayList<>();
        for(SymKey key: keys)
        {
            Map node = new HashMap();
            //node.put("id", key);
            //    "id": 1,
            //    "val": "密钥值, 被预值保护密钥加密, 最后进行hexdicial编码显示",
            //    "checkcode": "校验码,hexdicial编码显示,8字节长度",
            //    "version": "1"


        }
        return results;
    }

    @Path("/apply")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map apply(@FormParam("type") String type,
                     @FormParam("bits") String bits,
                     @FormParam("count") String count,
                     @FormParam("token") String token) {
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

            int iBits = Integer.parseInt(bits);
            int iCount = Integer.parseInt(count);

            List<SymKey> lists = new ArrayList<>();
            for(int i=0; i<iCount; i++)
            {
                SymKey symKey = symKeyService.apply(type, iBits);
                lists.add(symKey);
            }
            result.put("keys", convert(lists));

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
            apiLogService.addApiLog(ApiLogBuilder.builder("/api/skey/apply", ret, momo));
        }
        return result;
    }

    @Path("/apply")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map getApply(@FormParam("type") String type, @FormParam("token") String token) {
        return apply(type, "256", "10", token);
    }

    @Path("/recovery")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map recovery(@FormParam("startid") String startid,
                        @FormParam("limit") String limit,
                        @FormParam("token") String token) {
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

            List<SymKey> lists = new ArrayList<>();
            int iStart = Integer.parseInt(startid);
            int iLimit = Integer.parseInt(limit);

            for(int i=0; i<iLimit; i++)
            {
                SymKey symKey = symKeyService.fetch(Integer.toString(iStart+i));
                lists.add(symKey);
            }
            result.put("keys", convert(lists));

            result.put("success", true);
            result.put("retcode", 0);
            ret = true;
        } catch (Exception e) {
            result.put("success", false);
            result.put("retcode", 500);
            result.put("message", "server error");
            momo = "server error";
        } finally {
            apiLogService.addApiLog(ApiLogBuilder.builder("/api/skey/recovery", ret, momo));
        }
        return result;
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map update(@FormParam("startid") String startid,
                      @FormParam("limit") String limit,
                      @FormParam("token") String token) {
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

            List<SymKey> lists = new ArrayList<>();
            int iStart = Integer.parseInt(startid);
            int iLimit = Integer.parseInt(limit);

            for(int i=0; i<iLimit; i++)
            {
                SymKey symKey = symKeyService.fetch(Integer.toString(iStart+i));
                symKey = symKeyService.update(symKey);
                lists.add(symKey);
            }
            result.put("keys", convert(lists));
            result.put("success", true);
            result.put("retcode", 0);
            ret = true;
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
    public Map revoke(@FormParam("startid") String startid,
                      @FormParam("limit") String limit,
                      @FormParam("reason") String reason,
                      @FormParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        boolean ret = false;
        String momo = "";
        try {
            String s = tokenService.checkToken(token);
            if (s != null) {
                result.put("success", false);
                result.put("retcode", 400);
                result.put("message", s);
            }

            List<Map> lists = new ArrayList<>();
            int iStart = Integer.parseInt(startid);
            int iLimit = Integer.parseInt(limit);

            for(int i=0; i<iLimit; i++)
            {
                String keyId = Integer.toString(iStart+i);
                BooleanCarrier booleanCarrier = symKeyService.revoke(keyId, reason);
                Map status = new HashMap();
                status.put("id", keyId);
                status.put("message", booleanCarrier.getMessage());
                status.put("success",booleanCarrier.getResult());

                lists.add(status);
            }
            result.put("keys", lists);
            ret = true;

            result.put("success", true);
            result.put("retcode", 0);

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
