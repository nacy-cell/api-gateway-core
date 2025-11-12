package com.saka.gateway.session.defaults;

import com.saka.excuter.Executor;
import com.saka.gateway.datasource.DataSource;
import com.saka.gateway.datasource.DataSourceFactory;
import com.saka.gateway.datasource.unpooled.UnpooledDataSourceFactory;
import com.saka.gateway.session.Configuration;
import com.saka.gateway.session.GatewaySession;
import com.saka.gateway.session.GatewaySessionFactory;

public class DefaultGatewaySessionFactory implements GatewaySessionFactory {

    private final Configuration configuration;

    public DefaultGatewaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GatewaySession openSession(String uri) {
        // 获取数据源连接信息：这里把 Dubbo、HTTP 抽象为一种连接资源
        DataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
        // 创建执行器
        Executor executor = configuration.newExecutor(dataSource.getConnection());
        // 创建会话：DefaultGatewaySession
        return new DefaultGatewaySession(configuration, uri, executor);
    }

}
