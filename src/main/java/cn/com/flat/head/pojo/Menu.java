package cn.com.flat.head.pojo;

import lombok.Data;

/**
 * Created by poney on 2019-10-04.
 */
@Data
public class Menu {

    private String menuId;

    private String menuName;

    private String menuIcon;

    private String parentId;

    private String menuUrl;

    private int level;

    private int weight;

    private int disabled;

    private String local;

    private boolean leaf;

    private String permToken;

}
