package cn.com.flat.head.service;

import cn.com.flat.head.pojo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConfigService {
    BooleanCarrier  updateJdbcConfig(Jdbc jdbc);

    Jdbc getJdbcConfig();

    List<LogConfig> getLogConfig();
    

    void editLogLevel(String logLevel);

    void editLogDays(String logDays);

    void editUiConfig(MultipartFile file, String copyright);

    SysLogo getUiInfo();


    void sendMail(Mail mail);
}
