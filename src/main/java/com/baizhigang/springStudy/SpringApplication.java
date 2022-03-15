package com.baizhigang.springStudy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        //使用FactoryBean注入方式，这里可以看到结果是Apple对象
//         Object myFactoryBean = applicationContext.getBean("MyFactoryBean");
//        System.out.println(myFactoryBean); //com.baizhigang.springStudy.factoryBean.Apple@4206a205
    }
}
