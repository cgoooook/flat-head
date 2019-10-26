package cn.com.flat.head.controller.management;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Organization;
import cn.com.flat.head.service.OrgService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
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

    @DeleteMapping("/{id}")
    @ResponseBody
    public AjaxResponse deleteOrg(@PathVariable("id") int id, HttpSession session) {
        boolean b = orgService.deleteOrgById(id);
        return AjaxResponse.getInstanceByResult(b, session);
    }
}
