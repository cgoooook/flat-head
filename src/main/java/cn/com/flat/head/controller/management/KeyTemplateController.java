package cn.com.flat.head.controller.management;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.KeyTemplate;
import cn.com.flat.head.service.KeyTemplateService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@Controller
@RequestMapping("/key/template")
public class KeyTemplateController {

    @Autowired
    private KeyTemplateService keyTemplateService;

    @GetMapping
    public String keyTemplatePage() {
        return "management/template";
    }

    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<KeyTemplate> getTemplateList(Pageable pageable, KeyTemplate keyTemplate) {
        List<KeyTemplate> templateListPage = keyTemplateService.getTemplateListPage(keyTemplate, pageable);
        return new DataTablesResponse<>(pageable, templateListPage);
    }

    @PostMapping("/status/{templateId}")
    @ResponseBody
    public AjaxResponse status(@PathVariable("templateId") String templateId, int status, HttpSession session) {
        boolean b = keyTemplateService.updateTemplateStatus(templateId, status);
        return AjaxResponse.getInstanceByResult(b, session);
    }

    @PutMapping
    @ResponseBody
    public AjaxResponse addTemplate(@RequestBody KeyTemplate template, HttpSession httpSession) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier booleanCarrier = keyTemplateService.addTemplate(template);
        if (booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.OK);
        } else {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
        }
        return ajaxResponse;
    }

    @DeleteMapping("/{templateId}")
    @ResponseBody
    public AjaxResponse delete(@PathVariable("templateId") String templateId, HttpSession session) {
        boolean b = keyTemplateService.deleteTemplate(templateId);
        return AjaxResponse.getInstanceByResult(b, session);
    }

    @GetMapping("/{templateId}")
    @ResponseBody
    public AjaxResponse getTemplate(@PathVariable("templateId") String templateId) {
        KeyTemplate keyTemplate = keyTemplateService.getKeyTemplate(templateId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setData(keyTemplate);
        return ajaxResponse;
    }

    @PostMapping
    @ResponseBody
    public AjaxResponse updateTemplate(KeyTemplate template, HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier booleanCarrier = keyTemplateService.updateTemplate(template);
        if (booleanCarrier.getResult()) {
            return ajaxResponse;
        } else {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
    }

}
