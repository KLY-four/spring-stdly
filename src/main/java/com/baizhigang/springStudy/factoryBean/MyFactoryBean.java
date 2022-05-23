package com.baizhigang.springStudy.factoryBean;

import com.baizhigang.springStudy.zhuru.AutoTest;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 实现spring的FactoryBean接口完成bean的注入
 * 然后我们需要将MyFactoryBean交给spring。这里采用@Bean,Import等方式都可以(如：@Import(MyFactoryBean.class))
 * */

/**
 * 这里注意：我们很明显注入的是MyFactoryBean这个对象，而不是Apple。但是为什么我们取出来的时候是Apple呢。
 * 这是因为由于该类的特殊性(一般正常的类不会有这种情况，往往是实现了框架提供的一些特殊接口，才会这样。如：FactoryBean),我们在获取对象时，
 * spring会调用MyFactoryBean的getObject方法，返回这个对象。
 * 所以我们拿到的Apple对象。(这种情况在spring整合mybatis中也有),当然这里实际上在ioc容器中的是MyFactoryBean，而不是Apple。
 * 只不过是在getBean时，spring是调用了yFactoryBean的getObject。
 * //todo 那么有没有什么办法可以返回这个MyFactoryBean对象呢?这是有的。 我们只需要在bean的id前加上 &即可或到原始对象MyFactoryBean。
 * //todo getBean("&myFactoryBean")
 * */

/**
 * 问题：我们getBean时既然spring是调用了getObject方法返回的内部对象，那么我们调用多次，是否会是多个不同的Apple对象呢。因为我们的getObject方法
 * 中是new 的对象。实际上spring内部是做过处理的，我们即使调用多次,该Apple都是同一个(单例前提下)
 *
 * Apple对象并不是在spring启动时创建，而是在getBean时创建(这个跟单例无关，因为Apple本省就不属于容器，是我们自己new的一个对象给到的MyFactory
 * Bean,所以Apple中的相关注解都是无法被spring解析的,可以认为Apple就是一个普通的对象)
 * */
@Component
public class MyFactoryBean implements FactoryBean {

//    @Autowired
//    @Qualifier("autoTestImpl1")
//    AutoTest autoTestImpl1;

    /**
     * @Lazy注解可以用来解决构造注入时的循环依赖问题。
     * 原理。
     * @Lazy 本质是基于cglib代理，在注入apple时，@Lazy会创建一个依赖类(下面以注入Apple类为例)的代理对象。然后将这个对象赋值给apple变量。
     * 而不是赋值为null。因此，构造器注入apple时，就不会依赖于Apple对象。以此来避免依赖注入。当这个代理对象在第一次使用时(调用函数或使用属性)
     * 代理对象内部会去创建或获取Apple对象，将其注入给apple。这就是第一次使用时加载依赖对象。后续再使用apple变量，那么就是直接使用Apple对象了。
     * 注意：
     * 1、apple变量赋值给其他变量，这是不会去创建依赖对象的。因为上面说过，正真创建或获取Apple对象的地方是代理对象内部。是要在代理对象使用时
     * 这个使用代表的是使用代理对象调用了里面的方法或属性，触发代理对象中的代码执行来为我们创建或获取Apple对象注入给apple。
     * 2、切勿在构造器中直接使用这个代理对象，否则又相当于在构造器中去创建依赖对象了，还是会循环依赖
     * 3、@Lazy虽然是对依赖对象的一个代理,但在创建代理对象时并不会创建依赖对象(因为cglib本质时继承，理论上来讲子类对象的创建也会触发父类对象的
     * 创建，但是这里并不会，因为这里要是创建父类对象，那么还是循环依赖了)
     * 4、apple在赋值给其他变量后，其他变量的使用也会触发依赖对象的创建。因为这本质还是使用了代理对象。
     *
     * */
    @Autowired
    @Lazy
    Apple apple;

    //需要注入的对象
    @Override
    public Object getObject() throws Exception {
        return new Apple();
    }

    {
        System.out.println(11);
    }

    //注入对象的类型
    @Override
    public Class<?> getObjectType() {
        return Apple.class;
    }

    //是否以单列模式注入
    @Override
    public boolean isSingleton() {
        return true;
    }

    @PostConstruct
    public void MyInit(){
        System.out.println("init ...fa");
        System.out.println(apple+"!!!");
      //  System.out.println(AutoTestImpl1);
    }

    @PreDestroy
    public void MyDestroy(){
        System.out.println("destroy...");

    }


}
