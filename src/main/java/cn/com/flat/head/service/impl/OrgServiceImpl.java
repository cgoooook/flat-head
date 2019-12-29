package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.dal.OrgDao;
import cn.com.flat.head.log.LoggerBuilder;
import cn.com.flat.head.log.OperateType;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.OrgTreeBo;
import cn.com.flat.head.pojo.Organization;
import cn.com.flat.head.service.OrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("orgService")
public class OrgServiceImpl implements OrgService {

    private static Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);

    @Autowired
    private LogDao logDao;

    @Autowired
    private OrgDao orgDao;

    @Override
    public List<Organization> getOrgListPage(Organization org, Pageable pageable) {
        PageableInterceptor.startPage(pageable);
        return orgDao.getOrgListPage(org);
    }

    @Override
    public boolean deleteOrgById(String id) {
        boolean result = true;
        try {
            result = orgDao.deleteOrgById(id);
        } catch (Exception e) {
            result = false;
            logger.error("delete org error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.deleteOrg, result, "delete org"));
        }

        return result;

    }

    @Override
    public BooleanCarrier addOrg(Organization org) {
        boolean result = true;
        BooleanCarrier b = new BooleanCarrier();
        try {
            Organization org1 = orgDao.getOrgByOrgName(org.getOrgName());
            if (null != org1) {
                b.setResult(false);
                b.setMessage("org.nameIsRepeat");
                return b;
            }
            Organization org2 = orgDao.getOrgByOrgCode(org.getOrgCode());
            if (null != org2) {
                b.setResult(false);
                b.setMessage("org.codeIsRepeat");
                return b;
            }
            Organization orgCarrier = orgDao.getOrgParentIdEqNegative1();
            org.setParentId(orgCarrier.getOrgId());
            org.setOrgId(UUID.randomUUID().toString());
            int num = orgDao.addOrg(org);
            if (num != 1) {
                b.setMessage("common.addError");
                b.setResult(false);
            } else {
                b.setResult(true);
            }
        } catch (Exception e) {
            b.setResult(false);
            result = false;
            logger.error("add org error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.addOrg, result, "add org name is" + org.getOrgName()));
        }

        return b;
    }

    @Override
    public Organization getOrgByOrgCode(String orgCode) {
        return orgDao.getOrgByOrgCode(orgCode);
    }

    @Override
    public Organization getOrgByOrgName(String orgName) {
        return orgDao.getOrgByOrgName(orgName);
    }

    @Override
    public Organization getOrgByOrgId(String orgId) {
        return orgDao.getOrgByOrgId(orgId);
    }

    @Override
    public BooleanCarrier editOrg(Organization org) {
        boolean result = true;
        BooleanCarrier b = new BooleanCarrier();
        try {
            b.setResult(true);
            String orgId = org.getOrgId();
            String orgName = org.getOrgName();
            String orgCode = org.getOrgCode();
            Organization orgByOrgId = orgDao.getOrgByOrgId(orgId);
            String oldName = orgByOrgId.getOrgName();
            String oldCode = orgByOrgId.getOrgCode();
            if (!orgName.equals(oldName)) {
                Organization orgByOrgName = orgDao.getOrgByOrgName(orgName);
                if (orgByOrgName != null) {
                    b.setResult(false);
                    b.setMessage("org.nameIsRepeat");
                    return b;
                }
            }
            if (!orgCode.equals(oldCode)) {
                Organization orgByOrgCode = orgDao.getOrgByOrgCode(org.getOrgCode());
                if (orgByOrgCode != null) {
                    b.setResult(false);
                    b.setMessage("org.codeIsRepeat");
                    return b;
                }
            }
            orgDao.editOrg(org);
        } catch (Exception e) {
            b.setResult(false);
            result = false;
            logger.error("edit org error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.updateOrg, result, "add org name is" + org.getOrgName()));
        }
        return b;
    }

    @Override
    public List<OrgTreeBo> devTreeList(String parentId) {
        return orgDao.devTreeList(parentId);
    }

    @Override
    public List<Organization> getTreeList() {
        return orgDao.getTreeList();
    }

    @Override
    public List<Organization> orgChildList() {
        return orgDao.orgChildList();
    }

    @Override
    public int getOrgNum() {
        return orgDao.getOrgNum();
    }


}
