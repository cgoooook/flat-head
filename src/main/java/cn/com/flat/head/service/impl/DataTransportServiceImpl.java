package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.*;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.DataTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by panzhuowen on 2019/12/1.
 */
@Service
public class DataTransportServiceImpl implements DataTransportService {

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private KeyDao keyDao;

    @Autowired
    private DevServiceDao devServiceDao;

    @Autowired
    private KeyCollectionDao keyCollectionDao;

    @Autowired
    private KeyTemplateDao keyTemplateDao;

    @Override
    public DataTransport exportData(String orgId) {
        DataTransport dataTransport = new DataTransport();
        dataTransport.setOrganizationList(getOrgList(orgId));
        List<Key> keyList = getKeyList(orgId);
        Set<String> templateIds = new HashSet<>();
        keyList.forEach(key -> templateIds.add(key.getTemplateId()));
        dataTransport.setKeyList(keyList);
        dataTransport.setTemplateList(getTemplateList(templateIds));
        dataTransport.setDeviceList(getDeviceList(orgId));
        dataTransport.setCollectionList(getKeyCollectionListByOrgId(orgId));
        return dataTransport;
    }

    private List<Organization> getOrgList(String orgId) {
        List<Organization> organizations = new ArrayList<>();
        Organization orgByOrgId = orgDao.getOrgByOrgId(orgId);
        organizations.add(orgByOrgId);
        organizations.add(orgDao.getOrgByOrgId(orgByOrgId.getParentId()));
        return organizations;
    }

    private List<Key> getKeyList(String orgId) {
        List<Key> keyByOrgId = keyDao.getKeyByOrgId(orgId);
        keyByOrgId.forEach(key -> {
            key.setKeyHistories(keyDao.getKeyHistory(key.getKeyId()));
        });
        return keyByOrgId;
    }

    private List<KeyTemplate> getTemplateList(Set<String> templateIds) {
        List<KeyTemplate> templateList = new ArrayList<>();
        templateIds.forEach(templateId -> {
            templateList.add(keyTemplateDao.getTemplateById(templateId));
        });
        return templateList;
    }

    private List<Device> getDeviceList(String orgId) {
        return devServiceDao.getDeviceListByOrgId(orgId);
    }

    private List<KeyCollection> getKeyCollectionListByOrgId(String orgId) {
        List<KeyCollection> collectionByOrgId = keyCollectionDao.getCollectionByOrgId(orgId);
        collectionByOrgId.forEach(keyCollection -> {
            keyCollection.setKeyIds(keyCollectionDao.getCollectionKeys(keyCollection.getCollectionId()));
        });
        return collectionByOrgId;
    }
}
