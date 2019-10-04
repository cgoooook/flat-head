package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.Menu;

import java.util.List;

/**
 * Created by poney on 2019-10-04.
 */
public interface MenuDao {

    List<Menu> getEnableMenuList();

    List<Menu> getLevelMenuList(int level);

    List<Menu> getMenuList();

}
