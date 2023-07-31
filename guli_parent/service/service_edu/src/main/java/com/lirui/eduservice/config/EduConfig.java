package com.lirui.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mapstruct.MapperConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：配置类
 * @description：TODO
 * @date ：2023/5/4 15:46
 */
@Configuration
//扫描接口包
@MapperScan("com.lirui.eduservice.mapper")
public class EduConfig {
    /**
     * 逻辑删除的插件
     * */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

