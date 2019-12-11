package cn.com.flat.head.integration;

import cn.com.flat.head.pojo.*;
import cn.com.flat.head.rest.annotation.FlatRestService;
import cn.com.flat.head.service.*;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by panzhuowen on 2019/12/8.
 */
@FlatRestService
@Path("/api/org")
public class OrgDevAndKeyRest {

    @Autowired
    private KeyService keyService;

    @Autowired
    private DevService devService;

    @Autowired
    private KeyCollectionService keyCollectionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OrgService orgService;

    @Path("/key")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getKeyMetadata(@FormParam("token") String token, @FormParam("id") String id,
                                              @FormParam("name") String name, @FormParam("orgid") String orgId, @FormParam("version") String version) {
        Map<String, Object> result = new HashMap<>();
        String s = tokenService.checkToken(token);
        if (s != null) {
            result.put("success", false);
            result.put("retcode", 400);
            result.put("message", s);
            return result;
        }
        String cid = tokenService.getCid(token);
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
        Organization orgByOrgCode = orgService.getOrgByOrgCode(orgId);
        if (orgByOrgCode == null) {
            result.put("retcode", 104);
            result.put("success", false);
            result.put("message", "org  can't find");
            return result;
        }
        if (!StringUtils.equalsIgnoreCase(key.getOrgId(), orgByOrgCode.getOrgId())) {
            result.put("retcode", 103);
            result.put("success", false);
            result.put("message", "can't find key by orgid");
            return result;
        }
        if (StringUtils.isBlank(version) || StringUtils.equalsIgnoreCase("0", version)) {
            result.put("retcode", 0);
            result.put("success", true);
            result.put("message", tokenService.convertKeyEnc(key.getKeyValue(), cid) + ":" + key.getCheckValue());
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
                    result.put("message", tokenService.convertKeyEnc(keyHistoryByVersion.getKeyValue(), cid) + ":" + keyHistoryByVersion.getCheckValue());
                }
            }
        }
        return result;
    }

    @Path("/device/add")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> deviceAdd(@Form DeviceRegisterVO deviceRegisterVO) {
        Map<String, Object> ret = new HashMap<>();
        String s = tokenService.checkToken(deviceRegisterVO.getToken());
        if (s != null) {
            ret.put("success", false);
            ret.put("retcode", 400);
            ret.put("message", s);
            return ret;
        }
        String result = checkDevInput(deviceRegisterVO);
        if (result != null) {
            ret.put("retcode", 102);
            ret.put("success", false);
            ret.put("message", result);
            return ret;
        }
        KeyCollection collectionByName = keyCollectionService.getCollectionByName(deviceRegisterVO.getKset());
        if (collectionByName == null) {
            ret.put("retcode", 104);
            ret.put("success", false);
            ret.put("message", "key set can't find");
            return ret;
        }
        Organization orgByOrgCode = orgService.getOrgByOrgCode(deviceRegisterVO.getOrgid());
        if (orgByOrgCode == null) {
            ret.put("retcode", 104);
            ret.put("success", false);
            ret.put("message", "org  can't find");
            return ret;
        }
        if (!StringUtils.equalsIgnoreCase(collectionByName.getOrgId(), orgByOrgCode.getOrgId())) {
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
        device.setOrgId(orgByOrgCode.getOrgId());
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

    @Path("/info")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> orgInfo(@FormParam("token") String token, @FormParam("orgid") String orgId) {
        Map<String, Object> ret = new HashMap<>();
        String s = tokenService.checkToken(token);
        if (s != null) {
            ret.put("success", false);
            ret.put("retcode", 400);
            ret.put("message", s);
            return ret;
        }
        Organization orgByOrgCode = orgService.getOrgByOrgCode(orgId);
        if (orgByOrgCode == null) {
            ret.put("success", false);
            ret.put("retcode", 104);
            ret.put("message", "can't find org");
            return ret;
        }
        List<KeyCollection> keyCollectionByOrgId = keyCollectionService.getKeyCollectionByOrgId(orgByOrgCode.getOrgId());
        Map<String, List<OrgKeyVO>> keySets = new HashMap<>();
        for (KeyCollection keyCollection : keyCollectionByOrgId) {
            List<Key> keyListByOrgId = keyService.getKeyListByOrgIdForRest(orgByOrgCode.getOrgId(), keyCollection.getCollectionId());
            List<OrgKeyVO> keyVOS = new ArrayList<>();
            keyListByOrgId.forEach(key -> {
                OrgKeyVO orgKeyVO = new OrgKeyVO();
                orgKeyVO.setId(key.getKeyId());
                orgKeyVO.setName(key.getKeyName());
                orgKeyVO.setValue(tokenService.convertKeyEnc(key.getKeyValue(), token));
                orgKeyVO.setCode(key.getCheckValue());
                orgKeyVO.setVersion(key.getVersion() + "");
                keyVOS.add(orgKeyVO);
            });
            keySets.put(keyCollection.getCollectionName(), keyVOS);
        }
        ret.put("success", true);
        ret.put("retcode", 0);
        ret.put("message", "");
        ret.put("keySets", keySets);
        ret.put("properties", orgByOrgCode.getProperties());
        return ret;
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
