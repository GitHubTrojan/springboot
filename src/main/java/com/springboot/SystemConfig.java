package com.springboot;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Vincent on 2017/3/13.
 * Description spring config reference doc version 1.5.2 mentioned that:
 * 24.3 Application property files
 * SpringApplication will load properties from application.properties files in the following locations and add them to the Spring Environment:
 * <p>1. A /config subdirectory of the current directory. 当前目录的/config 子目录</p><br/>
 * <p>2. The current directory. 当前目录</p><br/>
 * <p>3. A classpath /config package. 类路径下的/config 包内，即我们通常所讲的resource目录下再建一个/config包</p><br/>
 * <p>4. The classpath root. 类路径根目录下 即 resource目录</p><br/>
 * <p>The list is ordered by precedence (properties defined in locations higher in the list override those defined in lower locations).</p>
 * which means, take their priorities into consideration, we can put the application.properties file to the directories mentioned below.
 * 可以放在这如上的四个位置，并且这四个位置的先后顺序就代表着各自的权重/优先级
 * 用户自定义的属性文件 可以通过@Component注解来读入自定义位置的*.properties。 不过需要注意，
 * @author Vincent
 * @version 1.0.0
 * @date 2017-03-13 15:51:50
 */
@Component
public class SystemConfig {
    private static Properties properties;

    public SystemConfig() {
        init();
    }

    /**
     * initialize the SystemConfig and get the properties file.
     */
    private void init() {
        Resource resource = new ClassPathResource("config/application.properties");
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (properties == null){
            System.out.println("application.properties initialized failed.");
        }
        System.out.println("init server port:" + SystemConfig.getValue("server.port"));
    }

    public static Properties getProperties() {
        return properties;
    }

    /**
     * 根据给定的键值获取属性值
     * @return value
     */
    public static String getValue(String key){
        return properties == null ? null : properties.getProperty(key);
    }

}
