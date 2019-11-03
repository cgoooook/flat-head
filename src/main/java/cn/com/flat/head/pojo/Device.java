package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Device {
    private String  deviceId;
    private String deviceName;
    private String deviceCode;
    private String deviceIp;
    private String orgId;
    private String orgName;
    private String collectionId;
    private String collectionName;
    private List<KeyCollection> collectionIds;

}
