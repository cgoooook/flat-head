package cn.com.flat.head.service;

import cn.com.flat.head.pojo.Menu;

import java.util.List;
import java.util.Map;

/**
 * Created by poney on 2019-10-04.
 */
public interface MenuService {

    Map<String, Object> initMenuData();

    List<Menu> getMenuList();

}
