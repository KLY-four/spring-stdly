package com.baizhigang.springStudy.InitAndDestroyTest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 初始化 和 销毁方法指定的五种方式
 * 1、配置文件中在bean标签上使用init-method 和 destroy-method 指定初始化方法和销毁方法
 * 2、注解方式，在@bean注解中通过init-method 和 destroy-method属性指定
 * 3、使用@PostConstruct和@PreDestroy注解指定(直接标注在方法上，这两个注解不是spring提供的而是java规范提供的，但依然需要扫描器扫描，不然不生效)
 * 4、通过实现InitializingBean,DisposableBean来完成初始化操作和销毁操作。这种方式就不是我们自己定义方法了，而是使用接口的实现方法进行操作。
 * 5、实现BeanPostProcessor接口，该接口直接提供初始化和销毁方法
 * */

//@Component
public class MyInitTest implements InitializingBean, DisposableBean {
    //销毁方法
    @Override
    public void destroy() throws Exception {
        System.out.println("des ...");
    }
    //初始化方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init11 ...");
    }
}

/**
 * 第五种方式，适用于所有对象共同的处理操作。不适合单个bean的操作
 * 注意：第五种方式比较特殊，如果使用这种方法做初始化和销毁的话，他会将ioc容器中的所有bean都进行拦截，然后赋予他们初始化和销毁
 * 也就是说，我们这里虽然只有MyTest实现了这个接口，理论上就只应该为这个bean指定初始化和销毁方法，但实则不然，该接口会为容器中
 * 所有bean都赋予，所以我们会看到程序启动时，打印了很多此初始化信息
 *
 * 当然，初始化和销毁方法不存在覆盖，如：MyInitTest自己的初始化方法afterPropertiesSet同样也会执行，不会被覆盖，想当执行了两个初始化方法
 * */
//@Component
class MyTest implements BeanPostProcessor{

    //参数bean就是ioc容器的对象
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化 。。。");
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("销毁 。。。");
        return bean;
    }
}