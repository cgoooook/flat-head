package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.*;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.DataTransportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
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

    @Override
    public void importDat(InputStream inData) {
        try {
            int available = inData.available();
            byte[] data = new byte[available];
            IOUtils.readFully(inData, data);
            String dataJson = new String(data);
            ObjectMapper objectMapper = new ObjectMapper();
            DataTransport dataTransport = objectMapper.readValue(dataJson, DataTransport.class);
            List<Organization> organizationList = dataTransport.getOrganizationList();
            importOrg(organizationList);
            List<KeyTemplate> templateList = dataTransport.getTemplateList();
            importKetTemplate(templateList);
            List<KeyCollection> collectionList = dataTransport.getCollectionList();
            importCollection(collectionList);
            List<Device> deviceList = dataTransport.getDeviceList();
            importDevList(deviceList);
            List<Key> keyList = dataTransport.getKeyList();
            importKeyList(keyList);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    private void importKeyList(List<Key> keyList) {
        keyList.forEach(key -> {
            keyDao.deleteKeyById(key.getKeyId());
            keyDao.deleteKeyHistoryById(key.getKeyId());
            keyDao.addKey(key);
            List<KeyHistory> keyHistories = key.getKeyHistories();
            keyHistories.forEach(keyHistory -> {
                keyDao.addKeyHistorey(keyHistory);
            });
        });
    }

    private void importDevList(List<Device> deviceList) {
        deviceList.forEach(device -> {
            devServiceDao.deleteDevgById(device.getDeviceId());
            devServiceDao.addDev(device);
        });
    }

    private void importCollection(List<KeyCollection> collectionList) {
        collectionList.forEach(keyCollection -> {
            keyCollectionDao.deleteCollection(keyCollection.getCollectionId());
            keyCollectionDao.addCollection(keyCollection);
        });
    }

    private void importKetTemplate(List<KeyTemplate> keyTemplateList) {
        keyTemplateList.forEach(keyTemplate -> {
            keyTemplateDao.deleteTemplate(keyTemplate.getTemplateId());
            keyTemplateDao.addTemplate(keyTemplate);
        });
    }

    private void importOrg(List<Organization> organizations) {
        organizations.forEach(organization -> {
            orgDao.deleteOrgById(organization.getOrgId());
            orgDao.addOrg(organization);
        });
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
