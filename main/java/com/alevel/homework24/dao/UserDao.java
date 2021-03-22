package com.alevel.homework24.dao;

import com.alevel.homework24.model.User;
import com.alevel.homework24.util.HibernateSessionXmlFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UserDao {

    private SessionFactory sessionFactory = HibernateSessionXmlFactoryUtil.getSessionFactory();

    public void insertUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    public void updateUser(String email, Map<String, String> newValues) {
        String parameters = "";
        for (Map.Entry<String, String> item : newValues.entrySet()) {
            parameters += getDataBaseFieldName(item.getKey()) + " = '" + item.getValue() + "', ";
        }
        parameters = parameters.substring(0, parameters.length() - 2);
        String queryString = "UPDATE User SET " + parameters + " where email = '" + email + "'";

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(queryString);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    private static String getDataBaseFieldName(String field) {
        String dbField = "";
        switch (field) {
            case "first name":
            case "last name":
                String symbolForReplace = field.split(" ")[1].substring(0, 1);
                dbField = field.replace(" " + symbolForReplace, symbolForReplace.toUpperCase());
                break;
            case "date of birth":
                dbField = "birthday";
                break;
            case "address":
            case "email":
                dbField = field;
                break;
        }
        return dbField;
    }

    public void deleteUser(String email) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE email =: email");
            query.setParameter("email", email);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    public long getCountOfUsersByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT count(*) FROM User where email = :email", Long.class);
            query.setParameter("email", email);
            return query.getResultList().get(0);
        }
    }

    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("FROM User ORDER BY id", User.class);
            List<User> resultList = query.getResultList();
            session.getTransaction().commit();
            return resultList;
        }
    }

    public static User buildUser(String firstName, String lastName, LocalDate birthday, String address, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthday);
        user.setAddress(address);
        user.setEmail(email);
        return user;
    }

    public User getUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User where email = :email", User.class);
            query.setParameter("email", email);
            return query.getResultList().get(0);
        }
    }
}
