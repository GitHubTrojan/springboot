package com.springboot.controller;

import com.springboot.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by Vincent on 2017/3/13.
 * Version 1.0.0
 * Description a simple controller demo using spring config annotations.
 */
@RestController
@RequestMapping("/test")
public class HelloWorldController {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/hello")
    public Object hello(){
        logger.info("Hello World! Which means you have build the web application with spring config 'AutoConfiguration' mode successfully.");
        /*
         step 1 : test the request, the response value should be
         "Hello World! Which means you have build the web application with spring config 'AutoConfiguration' mode successfully"
         with quotes
          */
//        return "Hello World! Which means you have build the web application with spring config 'AutoConfiguration' mode successfully. 你好";
        /*
          step 2 test fastJson json serialization/formatter
           */
        Users user = new Users();
        user.setUid(1);
        user.setUname("Vincent Wang 汉字测试");
        return user;                                        //  response on thte page should be {"uid":1,"uname":"Vincent Wang"}
        /*
          step 3 test simple String response
          1. make the application.class extends the WebMvcConfigurerAdapter
          2. override the method 'configureMessageConverters' to reset the 'List<HttpMessageConverter<?>> converters'.
          3. disable step 2, enable step 1, sending a request on the browse or somewhere, see what happened.
           */
    }
}
