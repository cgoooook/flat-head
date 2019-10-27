package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Organization;

import java.util.List;
@RepositoryImpl
public interface OrgMapper {
    List<Organization> getOrgListPage(Organization org);

    Organization getOrgByOrgName(String orgName);

    Organization getOrgByOrcCode(String orgId);

    int addOrg(Organization org);

    Organization getOrgParentIdEqNegative1();

    boolean deleteOrgById(String orgId);

    Organization getOrgByOrgId(String orgId);

    int editOrg(Organization org);
}
