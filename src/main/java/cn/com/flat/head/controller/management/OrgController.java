package cn.com.flat.head.controller.management;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Organization;
import cn.com.flat.head.service.OrgService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/sys/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @GetMapping
    public String orgPage() {
        return "management/org";
    }


    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<Organization> orgList(Organization org, Pageable pageable) {
        List<Organization> orgListPage = orgService.getOrgListPage(org, pageable);
        return new DataTablesResponse<>(pageable, orgListPage);
    }

    @GetMapping("/childList")
    @ResponseBody
    public AjaxResponse orgChildList() {
        List<Organization> orgListPage = orgService.orgChildList();
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        ajaxResponse.setData(orgListPage);
        return ajaxResponse;
    }


    @PutMapping
    @ResponseBody
    public AjaxResponse addOrg(@RequestBody Organization org, HttpSession httpSession) {
        BooleanCarrier booleanCarrier = orgService.addOrg(org);
        if(!booleanCarrier.getResult()){
            AjaxResponse ajaxResponse = new AjaxResponse();
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return AjaxResponse.getInstanceByResult(booleanCarrier.getResult(), httpSession);
    }

    @PutMapping("/edit")
    @ResponseBody
    public AjaxResponse editOrg(@RequestBody Organization org, HttpSession httpSession) {

        BooleanCarrier booleanCarrier = orgService.editOrg(org);
        if(!booleanCarrier.getResult()){
            AjaxResponse ajaxResponse = new AjaxResponse();
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return AjaxResponse.getInstanceByResult(booleanCarrier.getResult(), httpSession);
    }




    @DeleteMapping("/{id}")
    @ResponseBody
    public AjaxResponse deleteOrg(@PathVariable("id") String id, HttpSession session) {
        boolean b = orgService.deleteOrgById(id);
        return AjaxResponse.getInstanceByResult(b, session);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResponse getOrg(@PathVariable("id") String orgId, HttpSession session) {
        Organization org = orgService.getOrgByOrgId(orgId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        ajaxResponse.setData(org);
        return ajaxResponse;
    }


    @GetMapping("/tree")
    @ResponseBody
    public AjaxResponse tree() {
        AjaxResponse ajaxResponse = new AjaxResponse();
        List<Organization> treeList = orgService.getTreeList();
        ajaxResponse.setData(treeList);
        return ajaxResponse;
    }


}
