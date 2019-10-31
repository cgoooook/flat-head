package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.OrgTreeBo;
import cn.com.flat.head.pojo.Organization;

import java.util.List;

public interface OrgService {
    List<Organization> getOrgListPage(Organization org, Pageable pageable);

    boolean deleteOrgById(String id);

    BooleanCarrier addOrg(Organization org);

    Organization getOrgByOrgCode(String orgCode);

    Organization getOrgByOrgName(String orgName);

    Organization getOrgByOrgId(String orgId);

    BooleanCarrier editOrg(Organization org);

    List<OrgTreeBo> devTreeList(String parentId);
}
