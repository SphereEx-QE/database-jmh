package com.sphereex.jmh.dbplueengine;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.apache.shardingsphere.infra.metadata.database.ShardingSphereDatabase;
import org.apache.shardingsphere.mode.manager.ContextManager;

import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShardingSpheres {
    
    @SneakyThrows
    public static DataSource createDataSource() {
        String configurationFile = System.getProperty("shardingsphere.configurationFile");
        DataSource result = YamlShardingSphereDataSourceFactory.createDataSource(new File(configurationFile));
        return Boolean.parseBoolean(System.getProperty("useRawDataSource")) ? getRawDataSource(result).orElse(result) : result;
    }
    
    @SneakyThrows
    private static Optional<DataSource> getRawDataSource(final DataSource dataSource) {
        Field field = ShardingSphereDataSource.class.getDeclaredField("contextManager");
        field.setAccessible(true);
        ContextManager contextManager = (ContextManager) field.get(dataSource);
        for (ShardingSphereDatabase each : contextManager.getMetaDataContexts().getMetaData().getDatabases().values()) {
            if (each.getResourceMetaData().getDataSources().isEmpty()) {
                continue;
            }
            return Optional.of(each.getResourceMetaData().getDataSources().values().iterator().next());
        }
        return Optional.empty();
    }
}
