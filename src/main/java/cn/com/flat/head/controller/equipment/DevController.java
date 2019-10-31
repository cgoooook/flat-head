package cn.com.flat.head.controller.equipment;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Device;
import cn.com.flat.head.pojo.OrgTreeBo;
import cn.com.flat.head.pojo.Organization;
import cn.com.flat.head.service.DevService;
import cn.com.flat.head.service.OrgService;
import cn.com.flat.head.web.DataTablesResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
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
         List<OrgTreeBo>  orgTrees  =   service.devTreeList(parentId);

         return orgTrees;
    }



}
