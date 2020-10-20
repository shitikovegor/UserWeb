package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Entity;
import com.shitikov.project.model.exception.DaoException;

import java.util.List;

public interface BaseDao<T extends Entity> {

    boolean add(T t, String password) throws DaoException;

    boolean remove(T t) throws DaoException;

    List<T> findAll() throws DaoException;
}
