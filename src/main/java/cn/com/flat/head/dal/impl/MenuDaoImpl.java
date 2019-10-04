package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.MenuDao;
import cn.com.flat.head.dal.mappers.MenuMapper;
import cn.com.flat.head.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by poney on 2019-10-04.
 */
@Repository
public class MenuDaoImpl implements MenuDao {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getEnableMenuList() {
        return menuMapper.getEnableMenuList();
    }

    @Override
    public List<Menu> getLevelMenuList(int level) {
        return menuMapper.getMenuListByLevel(level);
    }

    @Override
    public List<Menu> getMenuList() {
        return menuMapper.getMenuList();
    }
}
