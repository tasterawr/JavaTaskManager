package org.repository;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    T create(T entity) throws ClassNotFoundException, SQLException;
    T getEntity(int id);
    List<T> getAll();
    T update(T entity);
    void delete(int it);
}
