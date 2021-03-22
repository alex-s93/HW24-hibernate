package com.alevel.homework24.consoleModule;

import com.alevel.homework24.dao.CategoryDao;
import com.alevel.homework24.model.Category;

import java.util.List;
import java.util.Scanner;

class CategoryExecutor {
    private static Scanner scanner = new Scanner(System.in);
    private static CategoryDao categoryDao = new CategoryDao();

    static void createCategory() {
        System.out.println("Enter category name:");
        String name = scanner.nextLine();
        Category category = categoryDao.buildCategory(name);
        categoryDao.insertCategory(category);
    }

    static void updateCategory() {
        System.out.println("Enter category name which you want to update:");
        String oldName = scanner.nextLine();
        if (categoryDao.getCountOfCategoriesByName(oldName) == 0) {
            System.out.println("Category with this name is absent in the database. Try again");
            return;
        }

        System.out.println("Enter a new name for category:");
        String newName = scanner.nextLine();

        categoryDao.updateCategory(oldName, newName);
    }

    static void showCategories() {
        List<Category> categories = categoryDao.getAll();
        if (categories.size() == 0) {
            System.out.println("There are no categories in the database");
            return;
        }
        for (Category category : categories) {
            System.out.println(category);
        }
    }
}
