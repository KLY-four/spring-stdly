package com.baizhigang.springStudy.factoryBean;

import com.baizhigang.springStudy.zhuru.AutoTest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @PostConstruct 和 @PreDestroy 注解是java的jsr250规范提供的，并不属于Spring。
 * 但是该注解如果想被解析，也必须借助扫描器，如ComponentScan
 * */
@Component
public class Apple implements ApplicationContextAware, BeanNameAware {

    @Autowired
    AutoTest autoTest;

    public Apple(){
        System.out.println("Apple construct ..");
    }

    @PostConstruct
    public void MyInit(){
        System.out.println("init ...");
    }

    @PreDestroy
    public void MyDestroy(){
        System.out.println("destroy..."+autoTest);
    }

    public static void main(String[] args) {
        new Apple();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(autoTest);
        System.out.println(99999999);
    }

    @Override
    public void setBeanName(String s) {
        System.out.println(s);
    }
}
