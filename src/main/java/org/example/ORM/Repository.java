package org.example.ORM;

import java.util.List;
import java.util.function.Predicate;

public interface Repository <T> {
    List<T> get(Class<T> entityClass) throws Exception;
    void insert(Object entity) throws Exception;
    void insertAll(List<T> entities) throws Exception;
    List<T> find(Class<T> entityClass, Predicate<T> predicate) throws Exception;
    void update(Object entity, Predicate<T> predicate) throws Exception;
    void delete(Class<T> entityClass, Predicate<T> predicate) throws Exception;
    void close() throws Exception;
}
