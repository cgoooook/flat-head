package cn.com.flat.head.integration;

import cn.com.flat.head.pojo.*;
import cn.com.flat.head.rest.annotation.FlatRestService;
import cn.com.flat.head.service.DevService;
import cn.com.flat.head.service.KeyCollectionService;
import cn.com.flat.head.service.KeyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by panzhuowen on 2019/12/8.
 */
@FlatRestService
@Path("/apis/org")
public class OrgDevAndKeyRest {

    @Autowired
    private KeyService keyService;

    @Autowired
    private DevService devService;

    @Autowired
    private KeyCollectionService keyCollectionService;

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

    @Path("/device/add")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> deviceAdd(DeviceRegisterVO deviceRegisterVO) {
        Map<String, Object> ret = new HashMap<>();
        String result = checkDevInput(deviceRegisterVO);
        if (result != null) {
            ret.put("retcode", 102);
            ret.put("success", false);
            ret.put("message", result);
            return ret;
        }
        KeyCollection collectionByName = keyCollectionService.getCollectionByName(deviceRegisterVO.getName());
        if (collectionByName == null) {
            ret.put("retcode", 104);
            ret.put("success", false);
            ret.put("message", "key set can't find");
            return ret;
        }
        if (!StringUtils.equalsIgnoreCase(collectionByName.getOrgId(), deviceRegisterVO.getOrgid())) {
            ret.put("retcode", 104);
            ret.put("success", false);
            ret.put("message", "key set can't find in org");
            return ret;
        }
        Device device = new Device();
        device.setDeviceId(UUID.randomUUID().toString());
        device.setDeviceName(deviceRegisterVO.getName());
        device.setDeviceIp(deviceRegisterVO.getIp());
        device.setCollectionId(collectionByName.getCollectionId());
        device.setOrgId(deviceRegisterVO.getOrgid());
        device.setDeviceCode(deviceRegisterVO.getId());
        BooleanCarrier booleanCarrier = devService.addDev(device);
        if (!booleanCarrier.getResult()) {
            ret.put("retcode", 304);
            ret.put("success", false);
            ret.put("message", booleanCarrier.getMessage());
            return ret;
        } else {
            ret.put("retcode", 0);
            ret.put("success", true);
            ret.put("message", "");
            return ret;
        }
    }

    private String checkDevInput(DeviceRegisterVO deviceRegisterVO) {
        if (StringUtils.isBlank(deviceRegisterVO.getId())) {
            return "id not be null";
        }
        if (StringUtils.isBlank(deviceRegisterVO.getOrgid())) {
            return "orgid not be null";
        }
        if (StringUtils.isBlank(deviceRegisterVO.getName())) {
            return "name not be null";
        }
        if (StringUtils.isBlank(deviceRegisterVO.getKset())) {
            return "kest not be null";
        }
        if (StringUtils.isBlank(deviceRegisterVO.getIp())) {
            return "ip not be null";
        }
        return null;
    }

}
