package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Entity;
import com.shitikov.project.model.exception.DaoException;

import java.util.Map;
import java.util.Optional;

public interface BaseDao<E, T extends Entity> {

    boolean add(T t, String parameter) throws DaoException;

    Optional<T> findById(long id) throws DaoException;

    boolean update(E uniqueField, Map<String, String> parameters) throws DaoException;

    boolean remove(long id) throws DaoException;
    // TODO: 21.10.2020 is update required method in BaseDao if it exists in UserDao?

    default String fillParametersSQL(Map<String, String> parameters) {

        StringBuilder parametersSQL = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            parametersSQL.append(entry.getKey())
                    .append(" = \"")
                    .append(entry.getValue())
                    .append("\", ");
        }
        parametersSQL.delete(parametersSQL.lastIndexOf(","), parametersSQL.length());
        return parametersSQL.toString();
    }
}
