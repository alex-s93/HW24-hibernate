package com.alevel.homework24.consoleModule;

import com.alevel.homework24.dao.UserDao;
//import com.alevel.homework24.dbHelper.DBConnector;
import com.alevel.homework24.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class UserExecutor {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDao userDao = new UserDao();

    static void createUser() {
        System.out.println("Enter user's email:");
        String email = scanner.nextLine();
        if (userDao.getCountOfUsersByEmail(email) > 0) {
            System.out.println("This email already exists in the database. Try again.");
            return;
        }

        System.out.println("Enter user's first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter user's last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter user's date of birth in the format YYYY-MM-DD:");
        LocalDate birthday = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println("Enter user's home address:");
        String address = scanner.nextLine();

        User user = UserDao.buildUser(firstName, lastName, birthday, address, email);
        userDao.insertUser(user);
    }

    static void updateUser() {
        String[] userFields = {"first name", "last name", "date of birth", "address", "email"};

        System.out.println("Enter the email of the user you need to update:");
        String email = scanner.nextLine();
        if (userDao.getCountOfUsersByEmail(email) == 0) {
            System.out.println("User with this email is absent in the database. Try again");
            return;
        }

        System.out.println("What information do you want to update? Please enter one or more parameters from the brackets" +
                " (" + Arrays.toString(userFields) + ").");

        Map<String, String> newValues = new HashMap<>();
        do {
            System.out.println("Field:");
            String field = scanner.nextLine().toLowerCase();
            if (!Arrays.asList(userFields).contains(field)) {
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
        userDao.updateUser(email, newValues);
    }

    static void deleteUser() {
        System.out.println("Enter the email of user which you want to delete:");
        String email = scanner.nextLine();
        if (userDao.getCountOfUsersByEmail(email) == 0) {
            System.out.println("User with this email is absent in the database. Try again");
            return;
        }
        userDao.deleteUser(email);
    }

    static void showUsers() {
        List<User> users = userDao.getAll();
        if (users.size() == 0) {
            System.out.println("There are no users in the database");
            return;
        }
        for (User user: users) {
            System.out.println(user);
        }
    }
}
