package cn.com.flat.head.controller.key;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.DataTransportService;
import cn.com.flat.head.service.KeyService;
import cn.com.flat.head.service.OrgService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panzhuowen on 2019/11/5.
 */
@Controller
@RequestMapping("/key/key")
public class KeyController {

    @Autowired
    private KeyService keyService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private DataTransportService dataTransportService;


    @RequestMapping
    public String key() {
        return "key/key";
    }


    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<Key> getKeyListPage(Pageable pageable, Key key) {
        List<Key> keyListPage = keyService.getKeyListPage(pageable, key);
        return new DataTablesResponse<>(pageable, keyListPage);
    }


    @GetMapping("/keyGenInfo")
    @ResponseBody
    public AjaxResponse keyGenInfo() {
        AjaxResponse ajaxResponse = new AjaxResponse();
        Map<String, Object> data = new HashMap<>();
        List<Organization> keyGenOrg = keyService.getKeyGenOrg();
        List<KeyTemplate> keyGenTemplate = keyService.getKeyGenTemplate();
        data.put("orgList", keyGenOrg);
        data.put("templateList", keyGenTemplate);
        data.put("rootKey", keyService.checkRootKey());
        ajaxResponse.setData(data);
        return ajaxResponse;
    }

    @PutMapping
    @ResponseBody
    public AjaxResponse generateKey(@RequestBody Key key, HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        Subject subject = SecurityUtils.getSubject();
        key.setCreateBy(subject.getPrincipal().toString());
        BooleanCarrier booleanCarrier = keyService.addKey(key);
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            ajaxResponse.setReturnState(ReturnState.ERROR);
        }
        return ajaxResponse;
    }

    @PostMapping
    @ResponseBody
    public AjaxResponse updateKey(Key key,HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        Subject subject = SecurityUtils.getSubject();
        key.setCreateBy(subject.getPrincipal().toString());
        BooleanCarrier booleanCarrier = keyService.updateKey(key);
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
        }
        return ajaxResponse;
    }

    @PostMapping("/status/{keyId}")
    @ResponseBody
    public AjaxResponse updateKeyStatus(@PathVariable("keyId") String keyId, int status, HttpSession session) {
        return AjaxResponse
                .getInstanceByResult(keyService.updateKeyStatus(keyId, status), session);
    }

    @GetMapping("/detail/{keyId}")
    @ResponseBody
    public AjaxResponse keyDetail(@PathVariable("keyId") String keyId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        Map<String, Object> data = new HashMap<>();
        Key keyById = keyService.getKeyById(keyId);
        List<KeyHistory> keyHistory = keyService.getKeyHistory(keyId);
        data.put("key", keyById);
        data.put("historyList", keyHistory);
        ajaxResponse.setData(data);
        return ajaxResponse;
    }

    @GetMapping("/getOrg")
    @ResponseBody
    public AjaxResponse getOrgForDerive(String orgId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        Organization orgByOrgId = orgService.getOrgByOrgId(orgId);
        ajaxResponse.setData(orgByOrgId);
        return ajaxResponse;
    }

    @PostMapping("/importKey")
    @ResponseBody
    public AjaxResponse importKey(MultipartFile keyBackFile) throws IOException {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            dataTransportService.importDat(keyBackFile.getInputStream());
        } catch (Exception e) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg("key.importError");
        }
        return ajaxResponse;
    }

}
