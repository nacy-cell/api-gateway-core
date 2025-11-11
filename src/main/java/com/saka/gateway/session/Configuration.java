package com.saka.gateway.session;

import com.saka.gateway.bind.GenericReferenceRegistry;
import com.saka.gateway.bind.IGenericReference;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 *  * @description 会话生命周期配置项
 */
public class Configuration {

    private final GenericReferenceRegistry registry=new GenericReferenceRegistry(this);

    private final Map<String, ApplicationConfig> applicationConfigMap=new HashMap<>();
    private final Map<String, RegistryConfig> registryConfigMap=new HashMap<>();
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap=new HashMap<>();

    public Configuration(){
        // TODO 后期从配置中获取
        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-gateway-test");
        application.setQosEnable(false);

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setRegister(false);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("cn.bugstack.gateway.rpc.IActivityBooth");
        reference.setVersion("1.0.0");
        reference.setGeneric("true");

        applicationConfigMap.put("api-gateway-test", application);
        registryConfigMap.put("api-gateway-test", registry);
        referenceConfigMap.put("cn.bugstack.gateway.rpc.IActivityBooth", reference);

    }

    public  ApplicationConfig getApplicationConfig(String applicationName) {
        return  applicationConfigMap.get(applicationName);
    }

    public  RegistryConfig getRegistryConfig(String registryName) {
        return registryConfigMap.get(registryName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String referenceName){
        return referenceConfigMap.get(referenceName);
    }

    public void addGenericReference(String application, String interfaceName, String methodName) {
        registry.addGenericReference(application, interfaceName, methodName);
    }

    public IGenericReference getGenericReference(String methodName) {
        return registry.getGenericReference(methodName);
    }


}
