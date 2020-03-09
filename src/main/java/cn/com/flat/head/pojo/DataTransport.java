package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/13.
 */
@Data
public class DataTransport {

    private List<Organization> organizationList;

    private List<Key> keyList;

    private List<KeyTemplate> templateList;

    private List<Device> deviceList;

    private List<KeyCollection> collectionList;

}
