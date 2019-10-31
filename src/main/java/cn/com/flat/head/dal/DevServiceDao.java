package cn.com.flat.head.dal;


import cn.com.flat.head.pojo.Device;

import java.util.List;

public interface DevServiceDao {
    List<Device> getDevListPage(Device dev);

}
