package com.tan.springcloud2producer.configuration;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.tan.springcloud2producer.helper.PropertiesHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableApolloConfig
public class ApolloConfiguration {
    @Bean
    public PropertiesHelper sampleRedisConfig() {
        return new PropertiesHelper();
    }
}
