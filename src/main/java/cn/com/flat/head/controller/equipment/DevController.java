package cn.com.flat.head.controller.equipment;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.DevService;
import cn.com.flat.head.service.KeyCollectionService;
import cn.com.flat.head.service.OrgService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/sys/device")
public class DevController {
    @Autowired
    private DevService devService;
    @Autowired
    private OrgService service;
    @Autowired
    private KeyCollectionService collectionService;

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

        return service.devTreeList(parentId);
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

    @GetMapping("/{deviceCode}")
    @ResponseBody
    public AjaxResponse getOrg(@PathVariable("deviceCode") String deviceCode, HttpSession session) {
        Device dev = devService.getDevByDevCode(deviceCode);
        KeyCollection collectionByCollectionId = collectionService.getCollectionByCollectionId(dev.getCollectionId());
        dev.setCollectionName(collectionByCollectionId.getCollectionName());
        List<KeyCollection> keyCollectionByOrgId = collectionService.getKeyCollectionByOrgId(dev.getOrgId());
        dev.setCollectionIds(keyCollectionByOrgId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        ajaxResponse.setData(dev);
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


    @DeleteMapping("/devTreeList")
    @ResponseBody
    public AjaxResponse deleteDev(@PathVariable("deviceId") String deviceId, HttpSession session) {
        boolean b = devService.deleteDevById(deviceId);
        return AjaxResponse.getInstanceByResult(b, session);
    }


}
