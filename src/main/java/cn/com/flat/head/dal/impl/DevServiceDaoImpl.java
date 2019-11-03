package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.DevServiceDao;
import cn.com.flat.head.dal.mappers.DevMapper;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DevServiceDaoImpl implements DevServiceDao {
    @Autowired
    private DevMapper devMapper;
    @Override
    public List<Device> getDevListPage(Device dev) {
        return devMapper.getDevListPage(dev);
    }

    @Override
    public boolean deleteDevgById(String id) {
        return devMapper.deleteDevgById(id);
    }

    @Override
    public int addDev(Device dev) {

        return  devMapper.addDev(dev);
    }

    @Override
    public Device getDevById(String deviceId) {
        return devMapper.getDevById(deviceId);
    }

    @Override
    public Device getDevByName(String deviceName) {
        return devMapper.getDevByName(deviceName);

    }

    @Override
    public int getCollectionCountByCollectionId(String collectionId) {
        return devMapper.getCollectionCountByCollectionId(collectionId);
    }
}
