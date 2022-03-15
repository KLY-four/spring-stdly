package com.baizhigang.springStudy.importTest;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 无论哪种方式，都需要使用到@import注解
 * */
/**
 * 1、采用Import注解直接注入
 * */
@Import(MyImport.class)
//@Import(MyImportSelector.class) 第二种方式
//@Import(MyImportBeanDefinitionRegistrar.class) 第三种方式
public class MyImport {

}

/**
 * 2、实现ImportSelector
 * */
class MyImportSelector implements ImportSelector{

    //返回值为需要进行bean注入的全类名(可多个)，然后将该类放入@Import中
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.baizhigang.springStudy.importTest.MyImport"};
    }
}

/**
 * 第三种 实现ImportBeanDefinitionRegistrar
 * */
class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //这里参数 直接写全类名也可以
        BeanDefinition rootBeanDefinition = new RootBeanDefinition(MyImport.class);
        registry.registerBeanDefinition("bean的id",rootBeanDefinition);
    }
}
