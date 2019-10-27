package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.KeyTemplateDao;
import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.exception.KMSException;
import cn.com.flat.head.log.LoggerBuilder;
import cn.com.flat.head.log.OperateType;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.KeyTemplate;
import cn.com.flat.head.service.KeyTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@Service
public class KeyTemplateServiceImpl implements KeyTemplateService {

    private static Logger logger = LoggerFactory.getLogger(KeyTemplateServiceImpl.class);

    @Autowired
    private KeyTemplateDao keyTemplateDao;

    @Autowired
    private LogDao logDao;

    @Override
    public List<KeyTemplate> getTemplateListPage(KeyTemplate template, Pageable pageable) throws KMSException {
        try {
            PageableInterceptor.startPage(pageable);
            return keyTemplateDao.getTemplateList(template);
        } catch (Exception e) {
            logger.error("query template list error", e);
            throw new KMSException("get template list page error, cause by", e);
        }
    }

    @Override
    public boolean updateTemplateStatus(String templateId, int status) throws KMSException {
        boolean result = false;
        try {
            int ret = keyTemplateDao.updateTemplateStatus(templateId, status);
            result = ret == 1;
            return result;
        } catch (Exception e) {
            logger.error("update template status error", e);
            throw new KMSException("update template status error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.updateTemplate, result, "update status to " + status));
        }
    }
}
