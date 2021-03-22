package com.alevel.homework24.consoleModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ConsoleReader {
    private static Scanner scanner = new Scanner(System.in);

    static String getAction() {
        System.out.println("\nPlease select action for performing:");
        printAvailableCommands();

        String action = scanner.nextLine();
        if (getAvailableCommands().contains(action.toLowerCase())) {
            return action;
        } else {
            System.out.println("Impossible to perform the entered action. Try again!");
            return getAction();
        }
    }

    static void printAvailableCommands() {
        System.out.println(getAvailableCommands());
    }

    static List<String> getAvailableCommands() {
        List<String> commands = new ArrayList<>();
        Action[] actions = Action.values();
        for (Action action : actions) {
            commands.add(action.getActionName());
        }
        return commands;
    }
}
