package com.saka;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 泛化调用测试
 */
public class RPCTest {

    @Test
    public void test_rpc() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-gateway-test");
        application.setQosEnable(false);

        // 2. 配置注册中心（Zookeeper）
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setRegister(false);

        // 3. 配置泛化调用 ReferenceConfig
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("cn.bugstack.gateway.rpc.IActivityBooth");
        reference.setVersion("1.0.0");
        reference.setGeneric(true); // 3.x 中直接使用 boolean 类型，无需字符串 "true"

        // 4. 初始化 DubboBootstrap（3.x 推荐手动创建实例，而非使用 getInstance() 单例）
        DubboBootstrap bootstrap = DubboBootstrap.newInstance(); // 替换 getInstance()，避免单例复用冲突
        bootstrap.application(application)
                .registry(registry)
                .reference(reference)
                .start();

        // 5. 移除 ReferenceConfigCache（3.x 中已废弃），直接通过 reference.get() 获取 GenericService
        GenericService genericService = reference.get();

        // 6. 执行泛化调用（参数格式与 2.7.x 一致，无需修改）
        Object result = genericService.$invoke(
                "sayHi",
                new String[]{"java.lang.String"},
                new Object[]{"world"}
        );

        // 7. 打印结果
        System.out.println(result);

        // 8. 可选：关闭 DubboBootstrap（测试场景推荐，释放资源）
        bootstrap.stop();
    }

    @Test
    public void test_rpc_nacos() {

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();

        // 2. 应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-2x-generic-consumer");
        referenceConfig.setApplication(applicationConfig);

        // 3. 注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("nacos://127.0.0.1:8848");
        referenceConfig.setRegistry(registryConfig);

        // 4. 泛化调用核心配置
        referenceConfig.setInterface("com.saka.api.IGroupBuyNotifyService");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric(true); // 开启泛化调用（必须）
        referenceConfig.setTimeout(3000);

        // 5. 获取 GenericService 实例
        GenericService genericService = referenceConfig.get();

        // 6. 准备参数
        String[] parameterTypes = new String[]{"com.saka.api.dto.NotifyRequestDTO"};
        Map<String, Object> notifyRequestMap = new HashMap<>();
        notifyRequestMap.put("orderId", "123456");
        Object[] parameterValues = new Object[]{notifyRequestMap};

        // 7. 执行泛化调用（$invoke 方法用法完全一致）
        Object result = genericService.$invoke("groupBuyNotify", parameterTypes, parameterValues);

        // 8. 打印结果
        System.out.println("Dubbo 2.x 泛化调用成功，返回结果：" + result);
    }

}
