package LabFive;

import java.io.*;
        import java.util.*;

public class Commander {

    private CollectionManager manager;

    public Commander(CollectionManager manager) {
        this.manager = manager;
    }

    public void interactiveMod() throws IOException {
        try(Scanner commandReader = new Scanner(System.in)) {
            String userCommand = "";
            String[] finalUserCommand;
            while (!userCommand.equals("exit")) {
                userCommand = commandReader.nextLine();
                finalUserCommand = userCommand.trim().split(" ", 2);
                try {
                    switch (finalUserCommand[0]) {
                        case "": break;
                        case "remove_last":
                            manager.remove_last();
                            break;
                        case "add":
                            manager.add(finalUserCommand[1]);
                            break;
                        case "remove_greater":
                            manager.remove_greater(finalUserCommand[1]);
                            break;
                        case "show":
                            manager.show();
                            break;
                        case "clear":
                            manager.clear();
                            break;
                        case "info":
                            System.out.println(manager.toString());
                            break;
                        case "remove_all":
                            manager.remove_all(finalUserCommand[1]);
                            break;
                        case "load":
                            manager.load();
                            break;
                        case "remove":
                            manager.remove(finalUserCommand[1]);
                            break;
                        case "add_if_min":
                            manager.add_if_min(finalUserCommand[1]);
                            break;
                        case "help":
                            manager.help();
                            break;
                        case "exit":
                            manager.save();
                            break;
                        default:
                            System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Отсутствует аргумент. Синтаксис 'команда {element}'.");
                }
            }
        }
    }
}