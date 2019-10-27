package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.KeyTemplate;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/27.
 */
public interface KeyTemplateDao {

    List<KeyTemplate> getTemplateList(KeyTemplate template);

    int updateTemplateStatus(String templateId, int status);

}
