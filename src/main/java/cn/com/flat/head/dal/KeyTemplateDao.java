package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.KeyTemplate;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/27.
 */
public interface KeyTemplateDao {

    List<KeyTemplate> getTemplateList(KeyTemplate template);

    int updateTemplateStatus(String templateId, int status);

    int addTemplate(KeyTemplate template);

    int getTemplateCountByName(String name);

    int deleteTemplate(String templateId);

    KeyTemplate getTemplateById(String templateId);

    int updateTemplate(KeyTemplate template);

    List<KeyTemplate> getTemplateUse();

}
