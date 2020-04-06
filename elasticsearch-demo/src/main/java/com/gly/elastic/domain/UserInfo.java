package com.gly.elastic.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserInfo {

    private String name;
    private Integer age;
    private float salary;
    private String address;
    private String remark;
    private Date createTime;
    private String birthDate;

}
