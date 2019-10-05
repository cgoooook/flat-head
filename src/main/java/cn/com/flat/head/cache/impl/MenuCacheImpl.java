package cn.com.flat.head.cache.impl;

import cn.com.flat.head.cache.MenuCache;
import cn.com.flat.head.dal.MenuDao;
import cn.com.flat.head.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poney on 2019-10-05.
 */
@Service
public class MenuCacheImpl implements MenuCache {

    @Autowired
    private MenuDao menuDao;

    private Map<String, Menu> menuMap = new HashMap<>();

    @PostConstruct
    public void init() throws Exception {
        List<Menu> enableMenuList = menuDao.getEnableMenuList();
        for (Menu menu : enableMenuList) {
            menuMap.put(menu.getMenuId(), menu);
        }
    }


    @Override
    public Menu getMenu(String menuId) {
        return menuMap.get(menuId);
    }
}
