package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by poney on 2019-10-04.
 */
@Data
public class Role {

    private String roleId;

    private String roleName;

    private String roleDescription;

    private List<String> permTokens;

    private List<String> permIds;

}
