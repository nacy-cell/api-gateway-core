package com.saka.gateway.core.datasource;

import com.saka.gateway.core.session.Configuration;

public interface DataSourceFactory {

    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();

}
