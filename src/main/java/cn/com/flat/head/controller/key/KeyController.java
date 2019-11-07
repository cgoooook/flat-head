package cn.com.flat.head.controller.key;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Key;
import cn.com.flat.head.service.KeyService;
import cn.com.flat.head.web.DataTablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

}
