package com.baizhigang.springStudy.condition;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * spring提供的条件注解学习
 * 该注解主要用于注入bean时的条件判断(只有满足条件时才能注入bean)
 * */

//当MyCondition返回ture是 则可以注入bean。
//Conditional中可以同时提供多个Condition的class，只要其中有一个返回false则不注入
@Service
@Conditional(MyCondition.class)
public class ConditionalTest {
}
