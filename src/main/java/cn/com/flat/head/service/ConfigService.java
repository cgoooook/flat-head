package cn.com.flat.head.service;

import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Jdbc;

public interface ConfigService {
    BooleanCarrier  updateJdbcConfig(Jdbc jdbc);

    Jdbc getJdbcConfig();
}
