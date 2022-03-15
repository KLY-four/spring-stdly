package com.baizhigang.springStudy.condition;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 使用conditional注解时，我们需要自定义条件(只需要写一个实现与Condition的实现类即可)
 * */
public class MyCondition implements Condition {
    /**
     * context 为应用的上下文
     * metadata 为注解元数据
     * */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //获取到环境对象
        //之前看过源码，我们知道spring的properties文件的数据都存放在environment中(ApplicationEnvironment)
        //当然不止是properties中的数据放在里面，包括System中的properties也在里面(还有其他的)
        //所以我们可以通过environment获取到properties文件中的数据，一级System中的key-value数据(jvm中配置的-Dkey-value最终都是
        // 写入到SysTem类的properties中，因此这里我们也可以获取到这些jvm参数)
        final Environment environment = context.getEnvironment();
        String condition = environment.getProperty("condition");
        //获取应用中有该配置则返回true，表明可以注入bean
        if(StringUtils.equals(condition,"fill")){
            return true;
        }
        return false;
    }
}
