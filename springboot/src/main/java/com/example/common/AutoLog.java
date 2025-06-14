package com.example.common;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
    String value() default "";
}
//切面注解，用于监控接口调用信息