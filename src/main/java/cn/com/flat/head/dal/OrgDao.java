package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.Organization;

import java.util.List;

public interface OrgDao {
    List<Organization> getOrgListPage(Organization org);

    Organization getOrgByOrgName(String orgName);

    Organization getOrgByOrgOrgId(String orgId);

    int addOrg(Organization org);

    Organization getOrgParentIdEqNegative1();

    boolean deleteOrgById(String id);
}
