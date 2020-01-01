package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by poney on 2019-10-01.
 */
@Data
public class User {

    private String userId;

    private String nickName;

    private String username;

    private String password;

    private String pubKey;

    private int accountType;

    private String lang;

    private String remember;

    private String roleId;

    private Date lastLoginTime;



}
