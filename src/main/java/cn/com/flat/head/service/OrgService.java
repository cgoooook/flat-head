package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Organization;

import java.util.List;

public interface OrgService {
    List<Organization> getOrgListPage(Organization org, Pageable pageable);

    boolean deleteOrgById(String id);

    BooleanCarrier addOrg(Organization org);
}
