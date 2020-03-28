package com.gly.bean;

public class UserService {

    private static User initUser(){
        return new User().setAddress("杭州").setAge(16).setEmail("141424242@qq.com").setId(1).setIdentifyId("340322199810173487").setName("MM").setPhone("15923456798");
    }

    public static void main(String[] args) {
        User user = initUser();
        System.out.println(user.toString());
    }
}
