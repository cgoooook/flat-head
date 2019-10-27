package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.KeyTemplateDao;
import cn.com.flat.head.dal.mappers.KeyTemplateMapper;
import cn.com.flat.head.pojo.KeyTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@Repository
public class KeyTemplateDaoImpl implements KeyTemplateDao {

    @Autowired
    private KeyTemplateMapper keyTemplateMapper;


    @Override
    public List<KeyTemplate> getTemplateList(KeyTemplate template) {
        return keyTemplateMapper.getTemplateListPage(template);
    }

    @Override
    public int updateTemplateStatus(String templateId, int status) {
        return keyTemplateMapper.updateTemplateStatus(templateId, status);
    }
}