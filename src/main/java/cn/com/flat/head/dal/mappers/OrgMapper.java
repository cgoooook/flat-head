package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.Organization;

import java.util.List;
@RepositoryImpl
public interface OrgMapper {
    List<Organization> getOrgListPage(Organization org);

    Organization getOrgByOrgName(String orgName);

    Organization getOrgByOrgId(String orgId);

    int addOrg(Organization org);

    Organization getOrgParentIdEqNegative1();
}
