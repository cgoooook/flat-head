package cn.com.flat.head.pojo;

import lombok.Data;

@Data
public class Mail {
    private int id;
    private String addr;
    private int port;
    private int timeOut;
    private String sendMailbox;
    private String password;
    private String receivingMailbox;
}
