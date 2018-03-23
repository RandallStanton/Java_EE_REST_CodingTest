package com.codetest.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * Provides a JSR-299 @Producer for this applications JPA EntityManager.
 * Provides a @Producer for all class java.util.loggers
 */
public class EMProvider {

    @Produces
    @ApplicationScoped
    public EntityManager produceEntitymanager() {
	EntityManagerFactory fc = Persistence.createEntityManagerFactory("codetest");
	EntityManager entityManager = fc.createEntityManager();
	return entityManager;
    }

    public void close(@Disposes EntityManager entityManager) {
	try {
	    entityManager.flush();
	} finally {
	    entityManager.close();
	}
    }
}
