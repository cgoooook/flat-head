package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.OrgDao;
import cn.com.flat.head.dal.mappers.OrgMapper;
import cn.com.flat.head.pojo.OrgTreeBo;
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

    @Override
    public Organization getOrgByOrgName(String orgName) {
        return orgMapper.getOrgByOrgName(orgName);
    }


    @Override
    public int addOrg(Organization org) {
        return orgMapper.addOrg(org);
    }

    @Override
    public Organization getOrgParentIdEqNegative1() {
        return orgMapper.getOrgParentIdEqNegative1();
    }

    @Override
    public boolean deleteOrgById(String orgId) {
        return orgMapper.deleteOrgById(orgId);
    }

    @Override
    public Organization getOrgByOrgCode(String orgCode) {
        return orgMapper.getOrgByOrcCode(orgCode);
    }

    @Override
    public Organization getOrgByOrgId(String orgId) {
        return orgMapper.getOrgByOrgId(orgId);
    }

    @Override
    public int editOrg(Organization org) {
        return  orgMapper.editOrg(org);
    }

    @Override
    public List<OrgTreeBo> devTreeList(String parentId) {
        return  orgMapper.devTreeList(parentId);
    }

    @Override
    public List<Organization> getTreeList() {
        return orgMapper.getTreeList();
    }

    @Override
    public List<Organization> orgChildList() {
        return orgMapper.orgChildList();
    }
}
