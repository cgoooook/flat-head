package cn.com.flat.head.service.impl;

import cn.com.flat.head.exception.KMSException;
import cn.com.flat.head.pojo.ServerLog;
import cn.com.flat.head.service.ServerLogService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panzhuowen on 2019/11/17.
 */
@Service
public class ServerLogServiceImpl implements ServerLogService {
    @Override
    public List<ServerLog> getServerLogList() {
        List<ServerLog> serverLogs = new ArrayList<>();
        try {
            Resource resource = new FileUrlResource("logs/kms");
            if (resource.getFile().isDirectory()) {
                File[] files = resource.getFile().listFiles();
                if (files != null) {
                    for (File logFile : files) {
                        ServerLog serverLog = new ServerLog();
                        serverLog.setFileName(logFile.getName());
                        serverLogs.add(serverLog);
                    }
                }
            }
            return serverLogs;
        } catch (Exception e) {
            throw new KMSException("get server log file error");
        }
    }

    @Override
    public byte[] getFileContent(String fileName) {
        try {
            Resource resource = new FileUrlResource("logs/kms/" + fileName);
            InputStream inputStream = resource.getInputStream();
            int available = inputStream.available();
            byte[] fileContent = new byte[available];
            IOUtils.readFully(inputStream, fileContent);
            return fileContent;
        } catch (Exception e) {
            throw new KMSException("get server log file error");
        }
    }
}
