package com.baizhigang.springStudy;

import com.baizhigang.springStudy.factoryBean.Apple;
import com.baizhigang.springStudy.factoryBean.MyFactoryBean;
import com.baizhigang.springStudy.zhuru.AutoTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan
public class BeanConfiguration  {

//    @Bean
//    public MyFactoryBean myFactoryBean(){
//        return new MyFactoryBean();
//    }

//    @Bean("app")
    public Apple apple(){
        return new Apple();
    }
}
