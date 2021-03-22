package com.alevel.homework24.consoleModule;

public enum Action {
    ACTION_CREATE_PRODUCT("create product"),
    ACTION_UPDATE_PRODUCT("update product"),
    ACTION_SHOW_PRODUCTS("show products"),

    ACTION_CREATE_ORDER("create order"),
    ACTION_UPDATE_ORDER("update order"),
    ACTION_SHOW_ORDERS("show orders"),

    ACTION_CREATE_USER("create user"),
    ACTION_UPDATE_USER("update user"),
    ACTION_DELETE_USER("delete user"),
    ACTION_SHOW_USERS("show users"),

    ACTION_CREATE_CATEGORY("create category"),
    ACTION_UPDATE_CATEGORY("update category"),
    ACTION_SHOW_CATEGORIES("show categories"),

    ACTION_FINISH_APPLICATION("finish");

    private String actionName;

    Action(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public static Action getActionName(String name) {
        Action action = null;
        for (Action actionName : Action.values()) {
            if (actionName.getActionName().equals(name)) {
                action = actionName;
            }
        }
        return action;
    }

    @Override
    public String toString() {
        return actionName;
    }
}
