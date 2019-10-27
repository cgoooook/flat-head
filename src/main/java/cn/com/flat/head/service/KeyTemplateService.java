package cn.com.flat.head.service;

import cn.com.flat.head.exception.KMSException;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.KeyTemplate;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/27.
 */
public interface KeyTemplateService {

    List<KeyTemplate> getTemplateListPage(KeyTemplate template, Pageable pageable) throws KMSException;

    boolean updateTemplateStatus(String templateId, int status) throws KMSException;

}
