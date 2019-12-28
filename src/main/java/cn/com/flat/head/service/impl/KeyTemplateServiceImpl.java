package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.KeyDao;
import cn.com.flat.head.dal.KeyTemplateDao;
import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.exception.KMSException;
import cn.com.flat.head.log.LoggerBuilder;
import cn.com.flat.head.log.OperateType;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.KeyTemplate;
import cn.com.flat.head.service.KeyService;
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

    @Autowired
    private KeyDao keyDao;

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

    @Override
    public BooleanCarrier addTemplate(KeyTemplate template) throws KMSException {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        boolean result = false;
        try {
            //todo check time
            int count = keyTemplateDao.getTemplateCountByName(template.getTemplateName());
            if (count > 0) {
                booleanCarrier.setResult(false);
                booleanCarrier.setMessage("template.nameExists");
            } else {
                int ret = keyTemplateDao.addTemplate(template);
                result = ret == 1;
                booleanCarrier.setResult(result);
            }

        } catch (Exception e) {
            logger.error("add template error", e);
            booleanCarrier.setResult(false);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.addTemplate, result, "add template name is " + template.getTemplateName()));
        }
        return booleanCarrier;
    }

    @Override
    public BooleanCarrier deleteTemplate(String templateId) throws KMSException {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        boolean result = false;
        KeyTemplate templateById = keyTemplateDao.getTemplateById(templateId);
        try {
            int keyCountByTemplateId = keyDao.geyKeyCountByTemplateId(templateId);
            if (keyCountByTemplateId <= 0) {
                int ret = keyTemplateDao.deleteTemplate(templateId);
                result = ret == 1;
            } else {
               booleanCarrier.setMessage("template.usage");
            }

            booleanCarrier.setResult(result);
        } catch (Exception e) {
            logger.error("delete template error,", e);
        } finally {
            if (templateById != null) {
                logDao.addLog(LoggerBuilder.builder(OperateType.deleteTemplate, result, "delete template name is " + templateById.getTemplateName()));
            }
        }
        return booleanCarrier;
    }

    @Override
    public KeyTemplate getKeyTemplate(String keyTemplate) throws KMSException {
        return keyTemplateDao.getTemplateById(keyTemplate);
    }

    @Override
    public BooleanCarrier updateTemplate(KeyTemplate template) throws KMSException {
        boolean result = false;
        try {
            KeyTemplate templateById = keyTemplateDao.getTemplateById(template.getTemplateId());
            if (!templateById.getTemplateName().equals(template.getTemplateName())) {
                int templateCountByName = keyTemplateDao.getTemplateCountByName(template.getTemplateName());
                if (templateCountByName > 0) {
                    BooleanCarrier booleanCarrier = new BooleanCarrier();
                    booleanCarrier.setResult(false);
                    booleanCarrier.setMessage("template.nameExists");
                    return booleanCarrier;
                } else {
                    int ret = keyTemplateDao.updateTemplate(template);
                    result = ret == 1;
                    BooleanCarrier booleanCarrier = new BooleanCarrier();
                    booleanCarrier.setResult(result);
                    return booleanCarrier;
                }
            } else {
                int ret = keyTemplateDao.updateTemplate(template);
                result = ret == 1;
                BooleanCarrier booleanCarrier = new BooleanCarrier();
                booleanCarrier.setResult(result);
                return booleanCarrier;
            }

        } catch (Exception e) {
            logger.error("update template error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.updateTemplate, result, "update template template name is " + template.getTemplateName()));
        }
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        booleanCarrier.setResult(result);
        return booleanCarrier;
    }
}
