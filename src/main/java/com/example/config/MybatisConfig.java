package com.example.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        System.out.println("datasource configuration");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // 이건 service와 xml mapper를 사용할 때 필요함.
        // mappers 위치 설정
        Resource[] arrResource = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mappers/*Mapper.xml");
        sqlSessionFactoryBean.setMapperLocations(arrResource);
        return sqlSessionFactoryBean.getObject();
    }
}