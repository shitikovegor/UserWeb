package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Entity;
import com.shitikov.project.model.exception.DaoException;

import java.util.*;

public interface BaseDao<E, T extends Entity> {
    String QUESTION_MARK = " = ?, ";
    String COMMA = ",";

    boolean add(T t, String ... parameter) throws DaoException;

    Optional<T> findById(long id) throws DaoException;

    boolean update(E uniqueField, Map<String, String> parameters) throws DaoException;

    boolean remove(long id) throws DaoException;

    default List<String> fillParametersSQL(Map<String, String> parameters, StringBuilder parametersSQL) {

        List<String> values = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            parametersSQL.append(entry.getKey())
                    .append(QUESTION_MARK);
            values.add(entry.getValue());
        }
        parametersSQL.delete(parametersSQL.lastIndexOf(COMMA), parametersSQL.length());
        return values;
    }
}
