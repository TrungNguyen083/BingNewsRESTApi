package org.example.ORM;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CrudRepositoryImp<T> implements CrudRepository<T> {
    private final Connection connection;

    public CrudRepositoryImp() {
        this.connection = ConnectionManager.getConnection();
    }
    @Override
    public List<T> get(Class<T> entityClass) throws Exception {
        EntityMetadata metadata = EntityMetadataExtractor.extract(entityClass);
        String tableName = metadata.getTableName();
        String[] columnNames = metadata.getColumnNames();
        String sql = SqlGenerator.generateSelectAllSql(tableName, columnNames);
        List<T> items = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = EntityMapper.mapResultSetToObject(resultSet, entityClass);
                items.add(item);
            }
        }

        return items;
    }

    @Override
    public void insert(Object entity) throws Exception {
        EntityMetadata metadata = EntityMetadataExtractor.extract(entity.getClass());
        String tableName = metadata.getTableName();
        String sql = SqlGenerator.generateInsertSql(tableName, metadata.getColumnNames());

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            for (String columnName : metadata.getColumnNames()) {
                Object propertyValue = getPropertyValue(entity, columnName);
                preparedStatement.setObject(parameterIndex, propertyValue);
                parameterIndex++;
            }
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void insertAll(List<T> entities) throws Exception {

    }

    @Override
    public List<T> find(Class<T> entityClass, Predicate<T> predicate) throws Exception {
        EntityMetadata metadata = EntityMetadataExtractor.extract(entityClass);
        String tableName = metadata.getTableName();
        String sql = SqlGenerator.generateSelectAllSql(tableName, metadata.getColumnNames());
        List<T> items = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = EntityMapper.mapResultSetToObject(resultSet, entityClass);
                if (predicate.test(item)) {
                    items.add(item);
                }
            }
            return items;
        }
    }

    @Override
    public void delete(Class<T> entityClass, Predicate<T> predicate) throws Exception {
        EntityMetadata metadata = EntityMetadataExtractor.extract(entityClass);
        String tableName = metadata.getTableName();
        String sqlSelect = SqlGenerator.generateSelectAllSql(tableName, metadata.getColumnNames());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = EntityMapper.mapResultSetToObject(resultSet, entityClass);
                if (predicate.test(item)) {
                    String sql = SqlGenerator.generateDeleteSql(tableName, metadata.getPrimaryKeyField());
                    try (PreparedStatement prepared = connection.prepareStatement(sql)) {
                        prepared.setString(1, metadata.getPrimaryKeyValue(item).toString());
                        prepared.executeUpdate();
                    }
                }
            }
        }

    }

    @Override
    public void update(Object entity, Predicate<T> predicate) throws Exception {
        EntityMetadata metadata = EntityMetadataExtractor.extract(entity.getClass());
        String tableName = metadata.getTableName();
        String sqlSelect = SqlGenerator.generateSelectAllSql(tableName, metadata.getColumnNames());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = (T) EntityMapper.mapResultSetToObject(resultSet, entity.getClass());
                if (predicate.test(item)) {
                    String sql = SqlGenerator.generateUpdateSql(tableName, metadata.getColumnNames(), metadata.getPrimaryKeyField());
                    try (PreparedStatement prepared = connection.prepareStatement(sql)) {
                        int parameterIndex = 1;
                        for (String columnName : metadata.getColumnNames()) {
                            if (columnName.equals(metadata.getPrimaryKeyField())) continue;
                            Object propertyValue = getPropertyValue(entity, columnName);
                            prepared.setObject(parameterIndex, propertyValue);
                            parameterIndex++;
                        }
                        prepared.setString(parameterIndex, metadata.getPrimaryKeyValue(item).toString());
                        prepared.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public void close() {
        if (connection != null) {
            ConnectionManager.closeConnection(connection);
        }
    }

    private static Object getPropertyValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

}
