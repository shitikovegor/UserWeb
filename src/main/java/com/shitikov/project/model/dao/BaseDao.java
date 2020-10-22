package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Entity;
import com.shitikov.project.model.exception.DaoException;

import java.util.List;

public interface BaseDao<T extends Entity> {

    boolean add(T t, String parameter) throws DaoException;

    boolean remove(T t) throws DaoException;

    List<T> findAll() throws DaoException;
    // TODO: 21.10.2020 is update required method in BaseDao if it exists in UserDao?
}
