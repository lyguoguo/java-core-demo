package com.gly.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User {

    private int id;
    private String name;
    private String identifyId;
    private String phone;
    private String email;
    private String address;
    private int age;
}
