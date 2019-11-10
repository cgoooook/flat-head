package cn.com.flat.head.controller.key;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Key;
import cn.com.flat.head.pojo.KeyTemplate;
import cn.com.flat.head.pojo.Organization;
import cn.com.flat.head.service.KeyService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/status/{keyId}")
    @ResponseBody
    public AjaxResponse updateKeyStatus(@PathVariable("keyId") String keyId, int status, HttpSession session) {
        return AjaxResponse
                .getInstanceByResult(keyService.updateKeyStatus(keyId, status), session);
    }

}
