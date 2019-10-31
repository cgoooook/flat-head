package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.KeyTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@RepositoryImpl
public interface KeyTemplateMapper {

    int addTemplate(KeyTemplate template);

    int updateTemplate(KeyTemplate template);

    int updateTemplateStatus(@Param("templateId") String templateId, @Param("status") int status);

    List<KeyTemplate> getTemplateListPage(KeyTemplate template);

    int getTemplateCountByName(String name);

    int deleteTemplate(String templateId);

    KeyTemplate getTemplateById(String templateId);

}
