package cn.com.flat.head.service;

import cn.com.flat.head.exception.KMSException;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.KeyTemplate;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/27.
 */
public interface KeyTemplateService {

    List<KeyTemplate> getTemplateListPage(KeyTemplate template, Pageable pageable) throws KMSException;

    boolean updateTemplateStatus(String templateId, int status) throws KMSException;

    BooleanCarrier addTemplate(KeyTemplate template) throws KMSException;

    boolean deleteTemplate(String templateId) throws KMSException;

    KeyTemplate getKeyTemplate(String keyTemplate) throws KMSException;

    BooleanCarrier updateTemplate(KeyTemplate template) throws KMSException;

}
