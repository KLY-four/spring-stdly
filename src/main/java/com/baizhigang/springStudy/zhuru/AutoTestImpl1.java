package com.baizhigang.springStudy.zhuru;

import com.baizhigang.springStudy.factoryBean.Apple;
import com.baizhigang.springStudy.factoryBean.MyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class AutoTestImpl1 implements AutoTest{

//    @Autowired
//    Apple apple;

    @PostConstruct
    public void init(){
        System.out.println(11);
    }

    public AutoTestImpl1(){
        System.out.println(222);
    }
}
