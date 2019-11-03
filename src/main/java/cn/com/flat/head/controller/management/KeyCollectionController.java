package cn.com.flat.head.controller.management;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.KeyCollection;
import cn.com.flat.head.service.KeyCollectionService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by panzhuowen on 2019/11/3.
 */
@Controller
@RequestMapping("/key/collection")
public class KeyCollectionController {

    @Autowired
    private KeyCollectionService collectionService;

    @RequestMapping
    public String keyCollection() {
        return "management/keyCollection";
    }

    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<KeyCollection> collectionLustTables(Pageable pageable, KeyCollection collection) {
        List<KeyCollection> keyCollectionListPage = collectionService.getKeyCollectionListPage(pageable, collection);
        return new DataTablesResponse<>(pageable, keyCollectionListPage);
    }

    @PutMapping
    @ResponseBody
    public AjaxResponse addCollection(@RequestBody KeyCollection keyCollection, HttpSession session) {
        BooleanCarrier carrier = collectionService.addCollection(keyCollection);
        AjaxResponse ajaxResponse = new AjaxResponse();
        if (!carrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(carrier.getMessage());
        }
        return ajaxResponse;
    }

    @DeleteMapping("/{collectionId}")
    @ResponseBody
    public AjaxResponse deleteCollection(HttpSession session, @PathVariable("collectionId") String collectionId) {
        BooleanCarrier booleanCarrier = collectionService.deleteKeyCollection(collectionId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return ajaxResponse;
    }


    @GetMapping("/collection/{orgId}")
    @ResponseBody
    public AjaxResponse getCollectionListByOrgId(@PathVariable("orgId") String orgId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setData(collectionService.getKeyCollectionByOrgId(orgId));
        return ajaxResponse;
    }

    @GetMapping("/{collectionId}")
    @ResponseBody
    public AjaxResponse getCollectionById(@PathVariable("collectionId") String collectionId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        KeyCollection collectionByCollectionId = collectionService.getCollectionByCollectionId(collectionId);
        ajaxResponse.setData(collectionByCollectionId);
        return ajaxResponse;
    }

    @PostMapping
    @ResponseBody
    public AjaxResponse updateCollection(KeyCollection collection) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier booleanCarrier = collectionService.updateCollection(collection);
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return ajaxResponse;
    }

}
