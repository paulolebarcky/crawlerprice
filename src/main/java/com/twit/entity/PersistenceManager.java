package com.twit.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Fábrica de entitymanager.
 * 
 * @author paulo
 * @since 2012-09-09
 */
public abstract class PersistenceManager {

    public static final boolean DEBUG = true;
    /**
     *
     */
    private static EntityManagerFactory emf;

    public PersistenceManager() {
    }

    public static EntityManager getEntityManager() {

        if (emf == null) {
            createEntityManagerFactory();
        }
        return emf.createEntityManager();
    }

    public static void closeEntityManagerFactory() {

        if (emf != null) {
            emf.close();
            emf = null;
            if (DEBUG) {
                System.out.println("n*** Persistence finished at " + new java.util.Date());
            }
        }
    }

    protected static void createEntityManagerFactory() {

        emf = Persistence.createEntityManagerFactory("com.twit_twit_jar_1.0-SNAPSHOTPU");
        if (DEBUG) {
            System.out.println("n*** Persistence started at " + new java.util.Date());
        }
    }
}