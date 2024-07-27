package com.filipejosilva.online.tournament.persistence.dao;

import java.util.List;

public interface Dao<T>{

    /**
     * Gets a list of the call
     * @return class list
     */
    List<T> findAll();

    /**
     * Gets the Class
     * @param id the class id
     * @return the class
     */
    T findById(Integer id);

    /**
     * Saves or updates the class
     * @param tclass the class to be saved or updated
     * @return the saved or updated model
     */
    T saveOrUpdate(T tclass);

    /**
     * Deletes the class
     * @param id the id of the class to be deleted
     */
    void delete(Integer id);
}
