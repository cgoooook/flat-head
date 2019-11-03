package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.OrgDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.OrgTreeBo;
import cn.com.flat.head.pojo.Organization;
import cn.com.flat.head.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("orgService")
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgDao orgDao;
    @Override
    public List<Organization> getOrgListPage(Organization org, Pageable pageable) {
        PageableInterceptor.startPage(pageable);
        return orgDao.getOrgListPage(org);
    }

    @Override
    public boolean deleteOrgById(String id) {

        return  orgDao.deleteOrgById(id);
    }

    @Override
    public BooleanCarrier addOrg(Organization org) {
        Organization org1 =  orgDao.getOrgByOrgName(org.getOrgName());
        BooleanCarrier b = new BooleanCarrier();
        if(null != org1){
          b.setResult(false);
          b.setMessage("org.nameIsRepeat");
          return b;
        }
        Organization org2 =  orgDao.getOrgByOrgCode(org.getOrgCode());
        if(null != org2){
            b.setResult(false);
            b.setMessage("org.codeIsRepeat");
            return b;
        }
        Organization orgCarrier =  orgDao.getOrgParentIdEqNegative1();
        org.setParentId(orgCarrier.getOrgId());
        org.setOrgId(UUID.randomUUID().toString());
        int num = orgDao.addOrg(org);
        if(num!=1){
            b.setMessage("common.addError");
            b.setResult(false);
        }else {
            b.setResult(true);
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
        BooleanCarrier b = new BooleanCarrier();
        b.setResult(true);
        String orgId = org.getOrgId();
        String orgName = org.getOrgName();
        String orgCode = org.getOrgCode();
        Organization orgByOrgId = orgDao.getOrgByOrgId(orgId);
        String oldName = orgByOrgId.getOrgName();
        String oldCode = orgByOrgId.getOrgCode();
        if(!orgName.equals(oldName)){
            Organization orgByOrgName = orgDao.getOrgByOrgName(orgName);
            if(orgByOrgName!=null){
                b.setResult(false);
                b.setMessage("org.nameIsRepeat");
                return b;
            }
        }
        if(!orgCode.equals(oldCode)){
            Organization orgByOrgCode = orgDao.getOrgByOrgCode(org.getOrgCode());
            if(orgByOrgCode!=null){
                b.setResult(false);
                b.setMessage("org.codeIsRepeat");
                return b;
            }
        }
        orgDao.editOrg(org);
        return b;
    }

    @Override
    public List<OrgTreeBo> devTreeList(String parentId) {


        return  orgDao.devTreeList(parentId);
    }

    @Override
    public List<Organization> orgChildList() {
        return orgDao.orgChildList();
    }


}
