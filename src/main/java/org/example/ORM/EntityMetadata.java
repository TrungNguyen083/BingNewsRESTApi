package org.example.ORM;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityMetadata {
    private String tableName;
    private Map<String, String> columnMetadata;
    private String primaryKeyField;

    public EntityMetadata() {
        this.columnMetadata = new HashMap<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getColumnMetadata() {
        return columnMetadata;
    }

    public void addColumnMetadata(String fieldName, String columnName) {
        columnMetadata.put(fieldName, columnName);
    }

    public String getColumnName(String fieldName) {
        return columnMetadata.get(fieldName);
    }

    public String[] getColumnNames() {
        return columnMetadata.values().toArray(new String[0]);
    }

    public String getPrimaryKeyField() {
        return primaryKeyField;
    }

    public void setPrimaryKeyField(String primaryKeyField) {
        this.primaryKeyField = primaryKeyField;
    }

    public Object getPrimaryKeyValue(Object entity) throws Exception {
        if (primaryKeyField == null) {
            throw new IllegalArgumentException("Primary key field is not defined in metadata.");
        }

        Class<?> entityClass = entity.getClass();
        Field primaryKeyField = entityClass.getDeclaredField(this.primaryKeyField);
        primaryKeyField.setAccessible(true);

        return primaryKeyField.get(entity);
    }
}
