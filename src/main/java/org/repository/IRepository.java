package org.repository;

import java.util.List;

public interface IRepository<T> {
    void create(T entity);
    T getEntity(int id);
    List<T> getAll();
    void update(T entity);
    void delete(int id);
}
