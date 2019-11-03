package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.DevServiceDao;
import cn.com.flat.head.dal.mappers.DevMapper;
import cn.com.flat.head.pojo.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DevServiceDaoImpl implements DevServiceDao {
    @Autowired
    private DevMapper devMapper;
    @Override
    public List<Device> getDevListPage(Device dev) {
        return devMapper.getDevListPage(dev);
    }

    @Override
    public int getCollectionCountByCollectionId(String collectionId) {
        return devMapper.getCollectionCountByCollectionId(collectionId);
    }
}
