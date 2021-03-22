package com.alevel.homework24.consoleModule;

import com.alevel.homework24.dao.*;
import com.alevel.homework24.model.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

class OrderExecutor {
    private static Scanner scanner = new Scanner(System.in);
    private static ProductDao productDao = new ProductDao();
    private static UserDao userDao = new UserDao();
    private static OrderDao orderDao = new OrderDao();

    static void createOrder() {
        if (productDao.getAll().size() == 0) {
            System.out.println("There are no products in the database. Please create it before.");
            ProductExecutor.createProduct();
        }

        System.out.println("Choose and enter a product name from the list:");
        ProductExecutor.showProducts();
        Product product;
        String productName = scanner.nextLine();
        if (productDao.getCountOfProductsByName(productName) == 0) {
            System.out.println("Incorrect product name. Try again");
            return;
        }
        product = productDao.getProductByName(productName);

        System.out.println("Enter user's email");
        String email = scanner.nextLine();
        User user;
        if (userDao.getCountOfUsersByEmail(email) == 0) {
            System.out.println("This user not exists in the database. Please create it before.");
            UserExecutor.createUser();
        }
        user = userDao.getUserByEmail(email);

        System.out.println("Please chose and enter a status from the list:");
        System.out.println(Arrays.toString(Status.values()));
        String status = scanner.nextLine();
        if (!Arrays.toString(Status.values()).contains(status)) {
            System.out.println("Incorrect status. Try again.");
            return;
        }

        System.out.println("How many products you want to add to the order?");
        int count = Integer.parseInt(scanner.nextLine());

        Timestamp orderTime = Timestamp.valueOf(LocalDateTime.now());

        Order order = orderDao.buildOrder(product, user, status, count, orderTime);
        orderDao.insertOrder(order);
    }

    static void updateOrder() {
        String[] orderFields = {"product name", "user email", "count", "status"};

        System.out.println("Enter the id of the order you need to update:");
        long orderId = Long.parseLong(scanner.nextLine());
        if (orderDao.getCountOfOrdersById(orderId) == 0) {
            System.out.println("Order with this ID is absent in the database. Try again");
            return;
        }

        System.out.println("What information do you want to update? Please enter one or more parameters from the brackets" +
                " (" + Arrays.toString(orderFields) + ").");

        Map<String, String> newValues = new HashMap<>();
        do {
            System.out.println("Field:");
            String field = scanner.nextLine().toLowerCase();
            if (!Arrays.asList(orderFields).contains(field)) {
                System.out.println("The entered name of field does not correspond to any field in the database. Try again!");
                continue;
            }

            String newValue;
            if (field.equals("status")) {
                System.out.println("Please chose and enter a status from the list:");
                System.out.println(Arrays.toString(Status.values()));
                newValue = scanner.nextLine();
                if (!Arrays.toString(Status.values()).contains(newValue)) {
                    System.out.println("Incorrect status. Try again.");
                    return;
                }
            } else {
                System.out.println("New value:");
                newValue = scanner.nextLine();
                if (field.equals("user email") && userDao.getCountOfUsersByEmail(newValue) == 0) {
                    System.out.println("This user does not exist in the database. Please create a user with such email or use an existing user.");
                    return;
                }
                if (field.equals("product name") && productDao.getCountOfProductsByName(newValue) == 0) {
                    System.out.println("This product does not exist in the database. Please create a product with this name or use the existing product in the database.");
                    return;
                }
            }
            newValues.put(field, newValue);
            System.out.println("Are these all the fields that you want to update?");
        } while (!scanner.nextLine().equals("yes"));
        if (newValues.size() == 0) {
            System.out.println("Nothing to update. Try again");
            return;
        }
        orderDao.updateOrder(orderId, newValues);
    }

    static void showOrders() {
        List<Order> orders = orderDao.getAll();
        if (orders.size() == 0) {
            System.out.println("There are no orders in the database");
            return;
        }
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
