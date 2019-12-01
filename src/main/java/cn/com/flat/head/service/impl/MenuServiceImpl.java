package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.MenuDao;
import cn.com.flat.head.pojo.Menu;
import cn.com.flat.head.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poney on 2019-10-04.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    public Map<String, Object> initMenuData() {
        Subject currentUser = SecurityUtils.getSubject();
        List<Menu> level0List = new ArrayList<>(128);
        for (Menu menu : menuDao.getLevelMenuList(0)) {
            if (!StringUtils.isBlank(menu.getPermToken()) && currentUser.isPermitted(menu.getPermToken())) {
                level0List.add(menuUrlHandle(menu));
            }
        }
        List<Menu> level1List = menuDao.getLevelMenuList(1);
        Map<String, List<Menu>> level1Map = groupMenuList(level1List);

        List<Menu> level2List = menuDao.getLevelMenuList(2);
        Map<String, List<Menu>> level2Map = groupMenuList(level2List);

        List<Menu> level3List = menuDao.getLevelMenuList(3);
        Map<String, List<Menu>> level3Map = groupMenuList(level3List);

        Map<String, Object> map = new HashMap<>();
        map.put("level0List", level0List);
        map.put("level1Map", level1Map);
        map.put("level2Map", level2Map);
        map.put("level3Map", level3Map);
        return map;
    }

    public List<Menu> getMenuList() {
        return menuDao.getEnableMenuList();
    }

    private Menu menuUrlHandle(Menu menu) {
        if (menu.isLeaf()) {
            String url = menu.getMenuUrl();
            menu.setMenuUrl(url + (url.contains("?") ? "&" : "?") + "menuId=" + menu.getMenuId());
        }
        return menu;
    }

    private Map<String, List<Menu>> groupMenuList(List<Menu> menuList) {
        Subject currentUser = SecurityUtils.getSubject();
        Map<String, List<Menu>> map = new HashMap<>();
        for (Menu menu : menuList) {
            if (currentUser.isPermitted(menu.getPermToken())) {
                if (!map.containsKey(menu.getParentId())) {
                    map.put(menu.getParentId(), new ArrayList<>());
                }
                if (!"".equals(menu.getPermToken())) {
                    map.get(menu.getParentId()).add(menuUrlHandle(menu));
                }
            }

        }
        return map;
    }
}
