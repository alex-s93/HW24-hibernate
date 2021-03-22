package com.alevel.homework24.consoleModule;

import com.alevel.homework24.dao.CategoryDao;
import com.alevel.homework24.dao.ProductDao;
import com.alevel.homework24.model.Category;
import com.alevel.homework24.model.Product;

import java.util.*;

class ProductExecutor {
    private static Scanner scanner = new Scanner(System.in);
    private static ProductDao productDao = new ProductDao();
    private static CategoryDao categoryDao = new CategoryDao();

    static void createProduct() {
        System.out.println("Enter a product name:");
        String name = scanner.nextLine();
        if (productDao.getCountOfProductsByName(name) > 0) {
            System.out.println("Product with this name already exists in the database. Try again.");
            return;
        }

        System.out.println("Enter a category name:");
        String categoryName = scanner.nextLine();
        Category category = categoryDao.buildCategory(categoryName);

        System.out.println("Enter a product price:");
        double price = Double.parseDouble(scanner.nextLine().replace(",", "."));

        Product product = ProductDao.buildProduct(category, name, price);
        productDao.insertProduct(product);
    }

    static void updateProduct() {
        String[] productFields = {"name", "category name", "price"};

        System.out.println("Enter the name of the product you need to update:");
        String name = scanner.nextLine();
        if (productDao.getCountOfProductsByName(name) == 0) {
            System.out.println("Product with this name is absent in the database. Try again");
            return;
        }

        System.out.println("What information do you want to update? Please enter one or more parameters from the brackets" +
                " (" + Arrays.toString(productFields) + ").");

        Map<String, String> newValues = new HashMap<>();
        do {
            System.out.println("Field:");
            String field = scanner.nextLine().toLowerCase();
            if (!Arrays.asList(productFields).contains(field)) {
                System.out.println("The entered name of field does not correspond to any field in the database. Try again!");
                continue;
            }
            System.out.println("New value:");
            String newValue = scanner.nextLine();
            newValues.put(field, newValue);
            System.out.println("Are these all the fields that you want to update?");
        } while (!scanner.nextLine().equals("yes"));
        if (newValues.size() == 0) {
            System.out.println("Nothing to update. Try again");
            return;
        }
        productDao.updateProduct(name, newValues);
    }

    static void showProducts() {
        List<Product> products = productDao.getAll();
        if (products.size() == 0) {
            System.out.println("There are no products in the database");
            return;
        }
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
