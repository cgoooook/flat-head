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
    public String userPage() {
        return "management/org";
    }


    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<Organization> orgList(Organization org, Pageable pageable) {
        List<Organization> orgListPage = orgService.getOrgListPage(org, pageable);
        return new DataTablesResponse<>(pageable, orgListPage);
    }
    @PutMapping
    @ResponseBody
    public AjaxResponse addOrg(@RequestBody Organization org, HttpSession httpSession) {
        BooleanCarrier booleanCarrier = orgService.addOrg(org);
        if(booleanCarrier.getResult()==false){
            AjaxResponse ajaxResponse = new AjaxResponse();
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return AjaxResponse.getInstanceByResult(booleanCarrier.getResult(), httpSession);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public AjaxResponse deleteOrg(@PathVariable("id") int id, HttpSession session) {
        boolean b = orgService.deleteOrgById(id);
        return AjaxResponse.getInstanceByResult(b, session);
    }
}
