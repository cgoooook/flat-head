package cn.com.flat.head.service;

import cn.com.flat.head.pojo.ServerLog;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/17.
 */
public interface ServerLogService {

    List<ServerLog> getServerLogList();

    byte[] getFileContent(String fileName);

}
