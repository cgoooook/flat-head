package cn.com.flat.head.controller.equipment;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Device;
import cn.com.flat.head.pojo.OrgTreeBo;
import cn.com.flat.head.service.DevService;
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
    public AjaxResponse addDev(@RequestBody Device org, HttpSession httpSession) {
        BooleanCarrier booleanCarrier = devService.addDev(org);
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
