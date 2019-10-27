package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.OrgDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
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
    public boolean deleteOrgById(int id) {
        return false;
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
        Organization org2 =  orgDao.getOrgByOrgOrgId(org.getOrgId());
        if(null != org2){
            b.setResult(false);
            b.setMessage("org.idIsRepeat");
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
}
