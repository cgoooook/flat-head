package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.DeviceKeyPairBind;
import org.apache.ibatis.annotations.Param;

/**
 * Created by panzhuowen on 2019/12/2.
 */
@RepositoryImpl
public interface KeyPairBindMapper {

    int addDeviceKeyPair(DeviceKeyPairBind deviceKeyPairBind);

    String getKeyPairIdByPubKey(String pubKey);

    int revokeKey(@Param("keyPairId") String keyPairId,@Param("reason") String reason);

    int getUserKeyPair(String alg);

    int getRevokeKeyPair(String alg);

}
