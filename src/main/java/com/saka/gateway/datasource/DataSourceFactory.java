package com.saka.gateway.datasource;

import com.saka.gateway.session.Configuration;

public interface DataSourceFactory {

    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();

}
