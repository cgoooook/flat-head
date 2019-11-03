package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.DevServiceDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Device;
import cn.com.flat.head.service.DevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DevServiceImpl implements DevService {
    @Autowired
    private DevServiceDao devServiceDao;
    @Override
    public List<Device> getDevListPage(Device dev, Pageable pageable) {
        PageableInterceptor.startPage(pageable);
        return devServiceDao.getDevListPage(dev);


    }

    @Override
    public int getDevCountByCollectionId(String collectionId) {
        return devServiceDao.getCollectionCountByCollectionId(collectionId);
    }


}
