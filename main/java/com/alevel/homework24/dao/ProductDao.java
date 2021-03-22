package com.alevel.homework24.dao;

import com.alevel.homework24.model.Category;
import com.alevel.homework24.model.Product;
import com.alevel.homework24.util.HibernateSessionXmlFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;

public class ProductDao {

    private SessionFactory sessionFactory = HibernateSessionXmlFactoryUtil.getSessionFactory();
    private CategoryDao categoryDao = new CategoryDao();

    public void insertProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }
    }

    public long getCountOfProductsByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT count(*) FROM Product where name = :name", Long.class);
            query.setParameter("name", name);
            return query.getResultList().get(0);
        }
    }

    public Product getProductByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("FROM Product where name = :name", Product.class);
            query.setParameter("name", name);
            return query.getResultList().get(0);
        }
    }

    public static Product buildProduct(Category category, String name, double price) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        return product;
    }

    public void updateProduct(String name, Map<String, String> newValues) {
        System.out.println(newValues);
        final String categoryNameParam = "category name";
        String parameters = "";

        for (Map.Entry<String, String> item : newValues.entrySet()) {
            switch (item.getKey()) {
                case categoryNameParam:
                    parameters += "category =: category, ";
                    break;
                case "price":
                    parameters += item.getKey() + " = " + item.getValue().replace(",", ".") + ", ";
                    break;
                case "name":
                    parameters += item.getKey() + " = '" + item.getValue() + "', ";
                    break;
            }
        }
        parameters = parameters.substring(0, parameters.length() - 2);
        String queryString = "UPDATE Product SET " + parameters + " where name = '" + name + "'";

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(queryString);
            if (newValues.containsKey(categoryNameParam)) {
                String newCategoryName = newValues.get(categoryNameParam);
                Category category = categoryDao.buildCategory(newCategoryName);
                if (categoryDao.getCountOfCategoriesByName(newCategoryName) == 0) {
                    session.save(category);
                } else {
                    category.setId(categoryDao.getCategoryId(category));
                }
                query.setParameter("category", category);
            }
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    public List<Product> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Product> query = session.createQuery("FROM Product ORDER BY id", Product.class);
            List<Product> resultList = query.getResultList();
            session.getTransaction().commit();
            return resultList;
        }
    }
}
