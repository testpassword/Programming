package server.connections;

import server.CollectionManager;
import server.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Administrator extends Connection {

    private HashMap<String, AbstractCommand> commands;

    public Administrator(CollectionManager serverCollection) {
        super(serverCollection, new HashMap<>());
        commands = getAvailableCommands();
        commands.put("show", new ShowCommand(serverCollection));
        commands.put("remove_last", new RemoveLastCommand(serverCollection));
        commands.put("info", new InfoCommand(serverCollection));
        commands.put("clear", new ClearCommand(serverCollection));
        commands.put("save", new SaveCommand(serverCollection));
        commands.put("add", new AddCommand(serverCollection));
        commands.put("remove_greater", new RemoveGreaterCommand(serverCollection));
        commands.put("remove_all", new RemoveAllCommand(serverCollection));
        commands.put("remove", new RemoveCommand(serverCollection));
        commands.put("add_if_min", new AddIfMinCommand(serverCollection));
        commands.put("sort", new SortCommand(serverCollection));
        commands.put("load", new LoadCommand(serverCollection));
        commands.put("import", new ImportCommand(serverCollection));
        commands.put("help", new HelpCommand(commands));
        commands.put("man", new ManCommand(commands));
        commands.put("shutdown", new ShutdownCommand(serverCollection));
    }

    @Override
    public void run() {
        try (Scanner fromKeyboard = new Scanner(System.in)) {
            System.out.println("Создана администраторская сессия.");
            AbstractCommand error = new ErrorCommand();
            String command;
            while (!(command = fromKeyboard.nextLine()).equals(null)) {
                String[] parsedCommand = command.trim().split(" ", 2);
                if (parsedCommand.length == 1)
                    System.out.println(commands.getOrDefault(parsedCommand[0], error).execute());
                else System.out.println(commands.getOrDefault(parsedCommand[0], error)
                        .execute(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length)));
            }
        }
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "commands=" + commands +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Administrator)) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(commands, that.commands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commands);
    }
}