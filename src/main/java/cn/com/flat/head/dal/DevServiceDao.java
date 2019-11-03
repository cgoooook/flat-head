package cn.com.flat.head.dal;


import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Device;

import java.util.List;

public interface DevServiceDao {

    List<Device> getDevListPage(Device dev);

    int getCollectionCountByCollectionId(String collectionId);

    boolean deleteDevgById(String id);

    int addDev(Device org);

    Device getDevById(String deviceId);

    Device getDevByName(String deviceName);

    Device getDevByDevCode(String deviceCode);

    void editOrg(Device dev);
}
