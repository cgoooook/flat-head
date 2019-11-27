package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.DeviceKeyPair;

/**
 * Created by panzhuowen on 2019/11/26.
 */
@RepositoryImpl
public interface KeyPairMapper {

    int addKeyPair(DeviceKeyPair deviceKeyPair);

    DeviceKeyPair getDeviceKeyPair(String alg);

    int deleteKeyPair(String id);

    int getKeyPairNum(String alg);

}
