package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.Device;

import java.util.List;

@RepositoryImpl
public interface DevMapper {

    List<Device> getDevListPage(Device dev) ;

    int getCollectionCountByCollectionId(String collectionId);

    boolean deleteDevgById(String deviceId);

    Device getDevById(String deviceId);

    Device getDevByName(String deviceName);

    int addDev(Device dev);

    Device getDevByDevCode(String deviceCode);

    void editDev(Device dev);
}
