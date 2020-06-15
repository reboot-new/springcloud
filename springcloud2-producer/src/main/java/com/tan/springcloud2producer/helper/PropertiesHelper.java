package com.tan.springcloud2producer.helper;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PropertiesHelper {
//    @ApolloConfig
//    private Config config; //inject config for namespace application
//    @ApolloConfig("application")
//    private Config anotherConfig; //inject config for namespace application
//    @ApolloConfig("FX.apollo")
//    private Config yetAnotherConfig; //inject config for namespace FX.apollo
//    @ApolloConfig("application.yml")
//    private Config ymlConfig; //inject config for namespace application.yml
//
//    private Map<String,String> properties = new ConcurrentHashMap<String,String>();
//
//    //config change listener for namespace application
//    @ApolloConfigChangeListener
//    private void someOnChange(ConfigChangeEvent changeEvent) {
//        //update injected value of batch if it is changed in Apollo
//        System.out.println("遍历配置");
//
//    }
//
//    //config change listener for namespace application
//    @ApolloConfigChangeListener("application")
//    private void anotherOnChange(ConfigChangeEvent changeEvent) {
//        //do something
//    }
//
//    //config change listener for namespaces application, FX.apollo and application.yml
//    @ApolloConfigChangeListener({"application", "FX.apollo", "application.yml"})
//    private void yetAnotherOnChange(ConfigChangeEvent changeEvent) {
//        //do something
//    }
//
//    //example of getting config from Apollo directly
//    //this will always return the latest value of timeout
//    public int getTimeout() {
//        return config.getIntProperty("timeout", 200);
//    }
//
//    private class PropertiesKey{
//        public static final String TEST = "TEST";
//    }
}
