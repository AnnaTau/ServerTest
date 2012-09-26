/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author 1
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    
     static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            // org.hibernate.cfg.
            AnnotationConfiguration cfg=new AnnotationConfiguration().configure(HibernateUtil.class.getResource("/hibernate.cfg.xml"));
            sessionFactory = cfg.buildSessionFactory();

        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed. " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
     
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
