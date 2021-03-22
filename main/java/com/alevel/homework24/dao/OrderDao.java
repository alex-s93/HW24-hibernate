package com.alevel.homework24.dao;

import com.alevel.homework24.model.*;
import com.alevel.homework24.util.HibernateSessionXmlFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private SessionFactory sessionFactory = HibernateSessionXmlFactoryUtil.getSessionFactory();

    public void insertOrder(Order order) {
        System.out.println(order);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        }
    }

    public void updateOrder(long orderId, Map<String, String> newValues) {
        ProductDao productDao = new ProductDao();
        UserDao userDao = new UserDao();
        String parameters = "";

        for (Map.Entry<String, String> item : newValues.entrySet()) {
            switch (item.getKey()) {
                case "product name":
                    parameters += "product = :product, ";
                    break;
                case "count":
                    parameters += item.getKey() + " = '" + item.getValue() + "', ";
                    break;
                case "status":
                    parameters += "status = :status, ";
                case "user email":
                    parameters += "user = :user, ";
                    break;
            }
        }
        parameters = parameters.substring(0, parameters.length() - 2);
        String queryString = "UPDATE Order SET " + parameters + " where id = '" + orderId + "'";

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(queryString);
            if (newValues.containsKey("product name")) {
                query.setParameter("product", productDao.getProductByName(newValues.get("product name")));
            }
            if (newValues.containsKey("user email")) {
                query.setParameter("user", userDao.getUserByEmail(newValues.get("user email")));
            }
            if (newValues.containsKey("status")) {
                query.setParameter("status", getStatus(newValues.get("status")));
            }
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    private static Status getStatus(String name) {
        Status status = null;
        switch (name) {
            case "OPEN":
                status = Status.OPEN;
                break;
            case "IN_PROGRESS":
                status = Status.IN_PROGRESS;
                break;
            case "COMPLETED":
                status = Status.COMPLETED;
                break;
            case "CANCELED":
                status = Status.CANCELED;
                break;
        }
        return status;
    }

    public List<Order> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Order> query = session.createQuery("FROM Order ORDER BY id", Order.class);
            List<Order> resultList = query.getResultList();
            session.getTransaction().commit();
            return resultList;
        }
    }

    public Order buildOrder(Product product, User user, String statusName, int count, Timestamp orderDate) {
        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);
        order.setStatus(getStatus(statusName));
        order.setCount(count);
        order.setOrderDate(orderDate);
        return order;
    }

    public long getCountOfOrdersById(long orderId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT count(*) FROM Order where id = :id", Long.class);
            query.setParameter("id", orderId);
            return query.getResultList().get(0);
        }
    }
}
