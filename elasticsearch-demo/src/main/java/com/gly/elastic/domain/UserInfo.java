package com.gly.elastic.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserInfo {

    private String name;
    private Integer age;
    private float salary;
    private String address;
    private String remark;
    private Date createTime;
    private String birthDate;

}
