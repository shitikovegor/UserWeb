package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Entity;
import com.shitikov.project.model.exception.DaoException;

import java.util.*;

/**
 * The interface Base dao.
 *
 * @param <E> the type parameter
 * @param <T> the type parameter
 * @author Shitikov Egor
 * @version 1.0
 */
public interface BaseDao<E, T extends Entity> {
    String QUESTION_MARK = " = ?, ";
    String COMMA = ",";

    /**
     * Add boolean.
     *
     * @param t         the t
     * @param parameter the parameter
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean add(T t, String ... parameter) throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(long id) throws DaoException;

    /**
     * Update boolean.
     *
     * @param uniqueField the unique field
     * @param parameters  the parameters
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(E uniqueField, Map<String, String> parameters) throws DaoException;

    /**
     * Remove boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean remove(long id) throws DaoException;

    /**
     * Fill parameters sql list.
     *
     * @param parameters    the parameters
     * @param parametersSQL the parameters sql
     * @return the list
     */
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
