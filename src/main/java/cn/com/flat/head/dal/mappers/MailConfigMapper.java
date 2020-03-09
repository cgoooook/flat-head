package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.Mail;

@RepositoryImpl
public interface MailConfigMapper {
    void saveMail(Mail mail);
    Mail getMail();
    void update(Mail mail);
}

