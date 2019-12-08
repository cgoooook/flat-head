package cn.com.flat.head.integration;

import cn.com.flat.head.pojo.Key;
import cn.com.flat.head.pojo.KeyHistory;
import cn.com.flat.head.rest.annotation.FlatRestService;
import cn.com.flat.head.service.KeyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panzhuowen on 2019/12/8.
 */
@FlatRestService
@Path("/apis/org")
public class OrgDevAndKeyRest {

    @Autowired
    private KeyService keyService;

    @Path("/key")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getKeyMetadata(@FormParam("token") String token, @FormParam("id") String id,
                                              @FormParam("name") String name, @FormParam("orgid") String orgId, @FormParam("version") String version) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isBlank(name) && StringUtils.isBlank(id)) {
            result.put("retcode", 101);
            result.put("success", false);
            result.put("message", "keyId or name not be empty");
            return result;
        }
        if (StringUtils.isBlank(orgId)) {
            result.put("retcode", 101);
            result.put("success", false);
            result.put("message", "orgid not be empty");
            return result;
        }
        Key key;
        if (!StringUtils.isBlank(id)) {
            key = keyService.getKeyById(id);
        } else {
            key = keyService.getKeyByName(name);
        }
        if (key == null) {
            result.put("retcode", 102);
            result.put("success", false);
            result.put("message", "can't find key by name or id");
            return result;
        }
        if (!StringUtils.equalsIgnoreCase(key.getOrgId(), orgId)) {
            result.put("retcode", 103);
            result.put("success", false);
            result.put("message", "can't find key by orgid");
            return result;
        }
        if (StringUtils.isBlank(version) || StringUtils.equalsIgnoreCase("0", version)) {
            result.put("retcode", 0);
            result.put("success", true);
            result.put("message", "");
            return result;
        } else {
            if (!StringUtils.equalsIgnoreCase(version, key.getVersion() + "")) {
                KeyHistory keyHistoryByVersion = keyService.getKeyHistoryByVersion(key.getKeyId(), version);
                if (keyHistoryByVersion == null) {
                    result.put("retcode", 102);
                    result.put("success", false);
                    result.put("message", "can't find key version");
                } else {
                    result.put("retcode", 0);
                    result.put("success", true);
                    result.put("message", "");
                }
            }
        }
        return result;
    }

}
