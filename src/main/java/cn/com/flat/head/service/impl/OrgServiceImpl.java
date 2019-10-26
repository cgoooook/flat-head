package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.OrgDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Organization;
import cn.com.flat.head.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
