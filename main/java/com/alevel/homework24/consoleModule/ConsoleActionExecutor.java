package com.alevel.homework24.consoleModule;

import static com.alevel.homework24.consoleModule.Action.*;

public class ConsoleActionExecutor {

    public static void executeAction() {
        Action action;
        do {
            action = Action.getActionName(ConsoleReader.getAction());
            switch (action) {
                case ACTION_CREATE_USER:
                    UserExecutor.createUser();
                    break;
                case ACTION_UPDATE_USER:
                    UserExecutor.updateUser();
                    break;
                case ACTION_DELETE_USER:
                    UserExecutor.deleteUser();
                    break;
                case ACTION_SHOW_USERS:
                    UserExecutor.showUsers();
                    break;
                case ACTION_CREATE_PRODUCT:
                    ProductExecutor.createProduct();
                    break;
                case ACTION_UPDATE_PRODUCT:
                    ProductExecutor.updateProduct();
                    break;
                case ACTION_SHOW_PRODUCTS:
                    ProductExecutor.showProducts();
                    break;
                case ACTION_CREATE_ORDER:
                    OrderExecutor.createOrder();
                    break;
                case ACTION_UPDATE_ORDER:
                    OrderExecutor.updateOrder();
                    break;
                case ACTION_SHOW_ORDERS:
                    OrderExecutor.showOrders();
                    break;
                case ACTION_CREATE_CATEGORY:
                    CategoryExecutor.createCategory();
                    break;
                case ACTION_UPDATE_CATEGORY:
                    CategoryExecutor.updateCategory();
                    break;
                case ACTION_SHOW_CATEGORIES:
                    CategoryExecutor.showCategories();
                    break;
            }
        } while (!action.equals(ACTION_FINISH_APPLICATION));
    }
}
