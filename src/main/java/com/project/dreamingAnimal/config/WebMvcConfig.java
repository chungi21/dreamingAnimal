package com.project.dreamingAnimal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry){

            /* '/js/**'로 호출하는 자원은 '/static/js/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
            /* '/css/**'로 호출하는 자원은 '/static/css/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
            /* '/img/**'로 호출하는 자원은 '/static/img/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
            /* '/font/**'로 호출하는 자원은 '/static/font/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/");


    }




}
