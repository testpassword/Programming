package server;

import commands.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Класс {@code ServerConnection} представляет объект сервера, который манипулирует {@link CollectionManager}.
 * @author Артемий Кульбако
 * @version 1.2
 * @since 28.04.19
 */
public class ServerConnection implements Runnable {

    private CollectionManager serverCollection;
    private Socket incoming;
    private HashMap<String, AbstractCommand> availableCommands;

    /**
     * @param serverCollection обеспечивает доступ к коллекции.
     * @param incoming активное соединение с клиентской программой.
     * Команды, доступные клиенту, являются объектами {@link AbstractCommand}, хранящимися в
     * {@code HashMap <String, AbstractCommand> availableCommands}.
     */
    ServerConnection(CollectionManager serverCollection, Socket incoming) {
        this.serverCollection = serverCollection;
        this.incoming = incoming;
        availableCommands = new HashMap<>();
        availableCommands.put("show", new ShowCommand(serverCollection));
        availableCommands.put("remove_last", new RemoveLastCommand(serverCollection));
        availableCommands.put("info", new InfoCommand(serverCollection));
        availableCommands.put("clear", new ClearCommand(serverCollection));
        availableCommands.put("save", new SaveCommand(serverCollection));
        availableCommands.put("add", new AddCommand(serverCollection));
        availableCommands.put("remove_greater", new RemoveGreaterCommand(serverCollection));
        availableCommands.put("remove_all", new RemoveAllCommand(serverCollection));
        availableCommands.put("remove", new RemoveCommand(serverCollection));
        availableCommands.put("add_if_min", new AddIfMinCommand(serverCollection));
        availableCommands.put("sort", new SortCommand(serverCollection));
        availableCommands.put("load", new LoadCommand(serverCollection));
        availableCommands.put("import", new ImportCommand(serverCollection));
        availableCommands.put("help", new HelpCommand(serverCollection, availableCommands));
        availableCommands.put("man", new ManCommand(serverCollection, availableCommands));
    }

    /**
     * Запускает активное соединение с клиентом в новом {@link Thread}.
     */
    @Override
    public void run() {
        try (ObjectInputStream getFromClient = new ObjectInputStream(incoming.getInputStream());
             ObjectOutputStream sendToClient = new ObjectOutputStream(incoming.getOutputStream())) {
            sendToClient.writeObject("Соединение установлено.\nВы можете вводить команды.");
            AbstractCommand errorCommand = new AbstractCommand(null) {
                @Override
                public String execute() {
                    return "Неизвестная команда. Введите 'help' для получения списка команд.";
                }
            };
            while (true) {
                try {
                    String requestFromClient = (String) getFromClient.readObject();
                    System.out.print("Получено [" + requestFromClient + "] от " + incoming + ". ");
                    String[] parsedCommand = requestFromClient.trim().split(" ",2);
                    if (parsedCommand.length == 1)
                        sendToClient.writeObject(availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute());
                    else if (parsedCommand.length == 2)
                        sendToClient.writeObject(availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute(parsedCommand[1]));
                    System.out.println("Ответ успешно отправлен.");
                } catch (SocketException e) {
                    System.out.println(incoming + " отключился от сервера."); //Windows
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(incoming + " отключился от сервера."); //Unix
        }
    }

    @Override
    public String toString() {
        return "ServerConnection{" +
                "serverCollection=" + serverCollection +
                ", incoming=" + incoming +
                ", availableCommands=" + availableCommands +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerConnection)) return false;
        ServerConnection that = (ServerConnection) o;
        return Objects.equals(serverCollection, that.serverCollection) &&
                Objects.equals(availableCommands, that.availableCommands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverCollection, availableCommands);
    }
}