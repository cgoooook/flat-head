package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.Organization;

import java.util.List;

public interface OrgDao {
    List<Organization> getOrgListPage(Organization org);
}
