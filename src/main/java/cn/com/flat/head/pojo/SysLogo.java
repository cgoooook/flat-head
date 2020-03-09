package cn.com.flat.head.pojo;

import lombok.Data;

@Data
public class SysLogo {
    private int id;
    private byte[] logo;
    private int status;
    private String copyright;
}
