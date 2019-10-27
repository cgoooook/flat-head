package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@Data
public class KeyTemplate {

    private String templateId;

    private String templateName;

    private int isBuiltIn;

    private int node;

    private Date startDate;

    private Date endDate;

    private String keyUsages;

    private String extendUsages;

    private int status;

}
