package com.example.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements  WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 指定controller统一的接口前缀,相当于在url上拼了一个/api/xxx
        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }

    @Override
    //自定义拦截器，设置拦截规则（登录和注册界面不拦截）
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/admin/login")
                .excludePathPatterns("/api/files/**")
                .excludePathPatterns("/api/captcha")
                .excludePathPatterns("/api/gonggao/upload")
                .excludePathPatterns("/api/admin/register");

    }
}