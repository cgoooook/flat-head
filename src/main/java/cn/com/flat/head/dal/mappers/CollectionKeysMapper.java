package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/28.
 */
@RepositoryImpl
public interface CollectionKeysMapper {

    int addCollectionKeys(@Param("collectionId") String collectionId, @Param("keyIds") List<String> keyIds);

    int deleteByCollectionId(String collectionId);

    List<String> getCollectionKeyIds(String collectionId);


}
