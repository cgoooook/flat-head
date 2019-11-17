package cn.com.flat.head.controller.equipment;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.DevService;
import cn.com.flat.head.service.KeyCollectionService;
import cn.com.flat.head.service.KeyService;
import cn.com.flat.head.service.OrgService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/device")
public class DevController {
    @Autowired
    private DevService devService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private KeyCollectionService keyCollectionService;

    @Autowired
    private KeyService keyService;

    @GetMapping
    public String devicePage() {
        return "device/device";
    }

    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<Device> deviceList(Device dev, Pageable pageable) {
        List<Device> devListPage = devService.getDevListPage(dev, pageable);
        return new DataTablesResponse<>(pageable, devListPage);
    }

    @PostMapping("/devTreeList")
    @ResponseBody
    public  List<OrgTreeBo> devTreeList(String parentId) {
         if(StringUtils.isEmpty(parentId)){
             parentId ="-1";
         }

        return orgService.devTreeList(parentId);
    }

    @PutMapping
    @ResponseBody
    public AjaxResponse addDev(@RequestBody Device dev, HttpSession httpSession) {
        BooleanCarrier booleanCarrier = devService.addDev(dev);
        if(!booleanCarrier.getResult()){
            AjaxResponse ajaxResponse = new AjaxResponse();
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return AjaxResponse.getInstanceByResult(booleanCarrier.getResult(), httpSession);
    }

    @GetMapping("/getKeyCollections")
    @ResponseBody
    public AjaxResponse getOrg(String orgId, HttpSession session) {
        List<KeyCollection> keyCollectionByOrgId = keyCollectionService.getKeyCollectionByOrgId(orgId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        ajaxResponse.setData(keyCollectionByOrgId);
        return ajaxResponse;
    }

    @GetMapping("/{deviceId}")
    @ResponseBody
    public AjaxResponse getDevice(@PathVariable("deviceId") String deviceId, HttpSession session) {
        Device devByDevId = devService.getDevByDevId(deviceId);
        List<KeyCollection> keyCollectionByOrgId = keyCollectionService.getKeyCollectionByOrgId(devByDevId.getOrgId());
        List<Organization> treeList = orgService.getTreeList();
        Map<String, Object> data = new HashMap<>();
        data.put("orgList", treeList);
        data.put("collection", keyCollectionByOrgId);
        data.put("dev", devByDevId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        ajaxResponse.setData(data);
        return ajaxResponse;
    }

    @PutMapping("/edit")
    @ResponseBody
    public AjaxResponse editOrg(@RequestBody Device dev, HttpSession httpSession) {
        BooleanCarrier booleanCarrier = devService.editDev(dev);
        if(!booleanCarrier.getResult()){
            AjaxResponse ajaxResponse = new AjaxResponse();
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return AjaxResponse.getInstanceByResult(booleanCarrier.getResult(), httpSession);
    }


    @DeleteMapping("/{deviceId}")
    @ResponseBody
    public AjaxResponse deleteDev(@PathVariable("deviceId") String deviceId, HttpSession session) {
        boolean b = devService.deleteDevById(deviceId);
        return AjaxResponse.getInstanceByResult(b, session);
    }

    @GetMapping("/keysDetail")
    @ResponseBody
    public AjaxResponse getKeysDetail(String devId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        Device devByDevId = devService.getDevByDevId(devId);
        List<Key> collectionKeyByCollectionId = keyService.getCollectionKeyByCollectionId(devByDevId.getCollectionId());
        ajaxResponse.setData(collectionKeyByCollectionId);
        return ajaxResponse;
    }


}
