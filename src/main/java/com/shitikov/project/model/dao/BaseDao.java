package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Entity;
import com.shitikov.project.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends Entity> {

    boolean add(T t) throws DaoException;

    boolean remove(T t) throws DaoException;

    boolean remove(K id) throws DaoException;

    Optional<T> findById(K id) throws DaoException;

    List<T> findAll() throws DaoException;
}
