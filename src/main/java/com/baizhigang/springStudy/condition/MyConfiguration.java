package com.baizhigang.springStudy.condition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 除了像ConditionalTest中那样使用，在配置类中使用也是可以的
 * */
//这种配置类的情况下，如果直接标注在类上，那么只要返回true，内部所有标注Bean注解的对象都会注入(如果内部方法没有标注Conditional的前提下，否则
// 还需考虑内部的Conditional注解)
@Configuration
@Conditional(MyCondition.class)
public class MyConfiguration {

    @Bean
    //满足条件则注入
    @Conditional(MyCondition.class)
    public ConditionalTest get(){
        return new ConditionalTest();
    }
}
