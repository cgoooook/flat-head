package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.KeyHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/10.
 */
@RepositoryImpl
public interface KeyHistoryMapper {

    List<KeyHistory> getKeyHistory(String keyId);

    int adHistoryKey(KeyHistory keyHistory);

    KeyHistory getHisoryKeyByVersion(@Param("keyId") String keyId, @Param("version") String version);

    int delKeyHistoryByKeyId(String keyId);

}
