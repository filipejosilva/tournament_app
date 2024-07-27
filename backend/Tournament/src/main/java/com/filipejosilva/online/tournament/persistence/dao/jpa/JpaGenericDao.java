package com.filipejosilva.online.tournament.persistence.dao.jpa;

import com.filipejosilva.online.tournament.persistence.dao.Dao;
import com.filipejosilva.online.tournament.persistence.jpa.JpaSessionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * A generic jpa data access object to be used as a base for concrete jpa service implementations
 *
 * @param <T> the class type
 * @see Dao
 */
public abstract class JpaGenericDao<T> implements Dao<T> {

    protected JpaSessionManager sm;
    protected Class<T> classType;

    /**
     * Sets the session manager
     *
     * @param sm the session manager to set
     */
    @Autowired
    public void setSm(JpaSessionManager sm) {
        this.sm = sm;
    }

    public JpaGenericDao(Class<T> classType){
        this.classType = classType;
    }

    @Override
    public List<T> findAll() {
        EntityManager em = sm.getCurrentSession();

        //Using criteria query
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(classType);
        Root<T> root = criteriaQuery.from(classType);
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public T findById(Integer id) {
        EntityManager em = sm.getCurrentSession();

        return em.find(classType, id);
    }

    @Override
    public T saveOrUpdate(T modelObject) {
        EntityManager em = sm.getCurrentSession();

        return em.merge(modelObject);
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = sm.getCurrentSession();

        em.remove(em.find(classType, id));
    }
    
}
