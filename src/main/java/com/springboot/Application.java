package com.springboot;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * This is where the spring config start.
 * The references of the annotation '@SpringBootApplication' in Spring Official Docs.
 * "Many Spring Boot developers always have their main class annotated with @Configuration, @EnableAutoConfiguration and @ComponentScan.
 *  Since these annotations are so frequently used together (especially if you follow the best practices above), Spring Boot provides a convenient @SpringBootApplication alternative.
 *  The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration and @ComponentScan with their default attributes
 * Created by Vincent on 2017/3/13.
 * @author Vincent
 * @version 1.0.0
 * Description: Spring Boot Starter
 */
//@SpringBootApplication
    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(value = "com.springboot.*",includeFilters = @ComponentScan.Filter(classes = org.springframework.web.bind.annotation.ControllerAdvice.class))
@MapperScan(value = "com.springboot.mapper")
public class Application extends WebMvcConfigurerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * import datasource
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    /**
     * import sqlSession
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    /**
     * import transactionManager
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * this is a method of the WebMvcConfigurerAdapter.class
     * we can override the default value/achievements of spring config
     * and customize our own HttpMessageConverters.
     * @param converters a message converter list
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /*
            using the StringHttpMessageConverter to handle with simple String message.
        */
        StringHttpMessageConverter stringConverter= new StringHttpMessageConverter();
        stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(stringConverter);
        /*
            using the FastJsonHttpMessageConverter to handle these below.
            1. text/html;charset=UTF-8                              a page(htm/html/jsp etc.)
            2. application/json;charset=utf-8                       json data type response
            3. text/plain;charset=UTF-8                             a text or string etc.
            4. application/x-www-form-urlencoded;charset=utf-8      standard encoding type. convert form data to a key-value.
            ...
        */
        FastJsonHttpMessageConverter4 fastJsonConverter = new FastJsonHttpMessageConverter4();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        fastJsonConverter.setFastJsonConfig(fastJsonConfig);

        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        MediaType text_plain = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
        MediaType text_html = new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
        MediaType x_www_form_urlencoded_utf8 = new MediaType(MediaType.APPLICATION_FORM_URLENCODED, Charset.forName("UTF-8"));
        supportedMediaTypes.add(text_html);
        supportedMediaTypes.add(text_plain);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(x_www_form_urlencoded_utf8);

        fastJsonConverter.setSupportedMediaTypes(supportedMediaTypes);

        converters.add(fastJsonConverter);
        super.configureMessageConverters(converters);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
