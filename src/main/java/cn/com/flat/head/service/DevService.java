package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Device;

import java.util.List;

public interface DevService {
    List<Device> getDevListPage(Device dev, Pageable pageable);


    boolean deleteDevById(String id);

    BooleanCarrier addDev(Device org);
}
