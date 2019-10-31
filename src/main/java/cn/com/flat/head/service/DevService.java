package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Device;

import java.util.List;

public interface DevService {
    List<Device> getDevListPage(Device dev, Pageable pageable);


}
