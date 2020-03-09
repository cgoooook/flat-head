package cn.com.flat.head.integration;

import lombok.Data;

import javax.ws.rs.FormParam;

/**
 * Created by panzhuowen on 2019/12/8.
 */
@Data
public class DeviceRegisterVO {

    @FormParam("token")
    private String token;

    @FormParam("id")
    private String id;

    @FormParam("name")
    private String name;

    @FormParam("ip")
    private String ip;

    @FormParam("kset")
    private String kset;

    @FormParam("orgid")
    private String orgid;

}
