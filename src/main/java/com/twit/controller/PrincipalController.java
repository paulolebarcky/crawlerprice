package com.twit.controller;

import com.twit.entity.PersistenceManager;
import com.twit.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Classe abstrata para tratar funções genéricas de crud.
 *
 * @author paulo
 * @since 2012-09-09
 */
public abstract class PrincipalController<T> extends PersistenceManager implements Serializable {

    private T t;
    
    public static final Logger logger = Logger.getLogger(PrincipalController.class.getName());

    public PrincipalController(T t) {
        this.t = t;
    }

    public T create() throws Exception{
        logger.log(Level.INFO, "Create :{0}", this.toString());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(this.t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return this.t;
    }

    public T edit() throws NonexistentEntityException, Exception {
        logger.log(Level.INFO, "Edit :{0}", this.toString());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(this.t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return this.t;
    }

    public void remove(Integer id) throws NonexistentEntityException {
        logger.log(Level.INFO, "Remove :{0}", this.toString());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Object object;
            try {
                object = em.getReference(this.t.getClass(), id);
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The object " + this.t.getClass() 
                        + " with id " + id + " no longer exists.", enfe);
            }
            em.remove(object);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private List<T> findEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(this.t.getClass()));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<T> findEntities() {
        logger.log(Level.INFO, "FindEntities :{0}", this.toString());
        return findEntities(true, -1, -1);
    }

    public List<T> findEntities(int maxResults, int firstResult) {
        logger.log(Level.INFO, "FindEntities :{0}", this.toString());
        return findEntities(false, maxResults, firstResult);
    }

    public Object find(Integer id) {
        logger.log(Level.INFO, "FInd :{0}", this.toString());
        EntityManager em = getEntityManager();
        try {
            return em.find(this.t.getClass(), id);
        } finally {
            em.close();
        }
    }

    public int getCount() {
        logger.log(Level.INFO, "Count :{0}", this.toString());
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<T> rt = cq.from(this.t.getClass());
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public int getSequence(String sequenceName) {
        logger.log(Level.INFO, "Sequence :{0}", this.toString());
        
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<T> rt = cq.from(this.t.getClass());
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
            
        } finally {
            em.close();
        }
    }
}
