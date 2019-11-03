package cn.com.flat.head.controller.management;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.KeyCollection;
import cn.com.flat.head.service.KeyCollectionService;
import cn.com.flat.head.web.DataTablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
