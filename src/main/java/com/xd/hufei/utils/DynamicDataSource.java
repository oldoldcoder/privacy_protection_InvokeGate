package com.xd.hufei.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public Map<Object, Object> dataSourceMap = new HashMap<>();

    @Autowired
    public DynamicDataSource(DataSource defaultDataSource) {
        dataSourceMap.put("default", defaultDataSource); // 将默认数据源添加到数据源映射中
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources( dataSourceMap);
        super.afterPropertiesSet();
    }
    @Override
    protected DataSource determineTargetDataSource() {
        String dataSourceKey = (String) determineCurrentLookupKey();
        return (DataSource) dataSourceMap.get(dataSourceKey);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return threadLocal.get();
    }

    public void createDataSource(String name, String host, String port, String database, String username, String password){
        if(name == null){
            name = "new";
        }
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSourceMap.put(name,dataSource);
    }

    // 删除数据源对象
    public void removeDataSource(String name) {
        DataSource dataSource = (DataSource) dataSourceMap.remove(name);
        if (dataSource instanceof DruidDataSource) {
            ((DruidDataSource) dataSource).close();
        }
    }
    // 关闭所有数据源
    public void closeAllDataSources() {
        for (Object dataSource : dataSourceMap.values()) {
            if (dataSource instanceof DruidDataSource) {
                ((DruidDataSource) dataSource).close();
            }
        }
        dataSourceMap.clear();
    }
}
