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
     * Apple上也使用了@Lazy，在注入apple时不会因为apple变量上标注了@Lazy就不去创建Apple。而是会创建Apple
     * 因为对于Apple而言，这就是有人使用到了他，他就会创建。而变量在注入时，依赖的bean是否创建并不影响他的懒加载
     * 因为它始终都是创建一个代理对象(cglib代理)给这个变量(也就是下面的apple),在使用apple时才会获取真正的Apple对象。
     * 所以，当Apple和apple上都标注@Lazy时，并不是在使用apple时才会去创建Apple对象。而是在对apple进行注入
     * 时，就会创建Apple对象，只不过创建了没直接赋值给apple而已(因为apple是懒加载，此时还不能直接注入真正的Apple对象，而是要赋值一个代理对象。
     * 等apple真正使用时，采取获取真正的Apple实例)。这一点实际根据打印信息就可以看的出来，我们可以查看Apple初始化的时机(根据构造器或初始化方法打印)
     * 就可以很好的发现，在MyFactoryBean创建过程中 注入apple时，会去创建Apple对象。但这个对象不会直接赋值给apple而是先赋值一个代理对象。
     * 基于@Lazy的特性，它可以用来解决构造器注入时的循环依赖问题。因为懒加载可以使一个对象在创建过程中 先不依赖于某个对象。只在这个对象使用时才去获取这个二依赖的
     * 对象，这就避免了在对象创建时的依赖问题
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
