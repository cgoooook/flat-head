package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.OrgTreeBo;
import cn.com.flat.head.pojo.Organization;

import java.util.List;

public interface OrgDao {
    List<Organization> getOrgListPage(Organization org);

    Organization getOrgByOrgName(String orgName);

    int addOrg(Organization org);

    Organization getOrgParentIdEqNegative1();

    boolean deleteOrgById(String id);

    Organization getOrgByOrgCode(String orgCode);

    Organization getOrgByOrgId(String orgId);

    int editOrg(Organization org);

    List<OrgTreeBo> devTreeList(String parentId);

    List<Organization> getTreeList();

    List<Organization> orgChildList();


    int getOrgNum();
}
