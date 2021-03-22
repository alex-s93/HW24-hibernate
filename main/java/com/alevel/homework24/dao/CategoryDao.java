package com.alevel.homework24.dao;

import com.alevel.homework24.model.Category;
import com.alevel.homework24.util.HibernateSessionXmlFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDao {

    private SessionFactory sessionFactory = HibernateSessionXmlFactoryUtil.getSessionFactory();

    public void insertCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
    }

    public void updateCategory(String oldName, String newName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Category SET name =: newName WHERE name =: oldName");
            query.setParameter("newName", newName);
            query.setParameter("oldName", oldName);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    long getCategoryId(Category category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Category> query = session.createQuery("FROM Category WHERE name = :name", Category.class);
            query.setParameter("name", category.getName());
            long id = query.uniqueResult().getId();
            session.getTransaction().commit();
            return id;
        }
    }

    public long getCountOfCategoriesByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT count(*) FROM Category where name = :name", Long.class);
            query.setParameter("name", name);
            return query.getResultList().get(0);
        }
    }

    public List<Category> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Category> query = session.createQuery("FROM Category ORDER BY id", Category.class);
            List<Category> resultList = query.getResultList();
            session.getTransaction().commit();
            return resultList;
        }
    }

    public Category buildCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }
}
