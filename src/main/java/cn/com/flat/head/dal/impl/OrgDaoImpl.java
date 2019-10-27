package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.OrgDao;
import cn.com.flat.head.dal.mappers.OrgMapper;
import cn.com.flat.head.pojo.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class OrgDaoImpl implements OrgDao {
    @Autowired
    private OrgMapper orgMapper;
    @Override
    public List<Organization> getOrgListPage(Organization org) {
        return orgMapper.getOrgListPage(org);
    }
}
