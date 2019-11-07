package cn.com.flat.head.pojo;

import lombok.Data;

@Data
public class Jdbc {
    private String url;
    private String username;
    private String password;
    private String driver;
    private String maxIdle;
    private String maxActive;


}
