package cn.com.flat.head.service;

import cn.com.flat.head.pojo.DataTransport;

import java.io.InputStream;

/**
 * Created by panzhuowen on 2019/12/1.
 */
public interface DataTransportService {

    DataTransport exportData(String orgId);

    void  importDat(InputStream inData);

}
