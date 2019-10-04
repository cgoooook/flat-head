package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.Menu;

import java.util.List;

/**
 * Created by poney on 2019-10-04.
 */
@RepositoryImpl
public interface MenuMapper {

    List<Menu> getEnableMenuList();

    List<Menu> getMenuListByLevel(int level);

    List<Menu> getMenuList();

}
