package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Key;
import cn.com.flat.head.pojo.KeyTemplate;
import cn.com.flat.head.pojo.Organization;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/6.
 */
public interface KeyService {

    List<Key> getKeyListPage(Pageable pageable, Key key);

    List<Organization> getKeyGenOrg();

    List<KeyTemplate> getKeyGenTemplate();

    boolean checkRootKey();

    BooleanCarrier addKey(Key key);

    boolean updateKeyStatus(String keyId, int status);
}
