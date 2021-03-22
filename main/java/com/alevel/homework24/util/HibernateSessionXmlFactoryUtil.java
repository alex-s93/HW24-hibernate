package com.alevel.homework24.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionXmlFactoryUtil {
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    private HibernateSessionXmlFactoryUtil() {
        throw new UnsupportedOperationException();
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
