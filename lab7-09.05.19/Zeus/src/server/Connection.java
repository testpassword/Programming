package server;

import server.commands.*;
import server.managers.CollectionManager;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Класс {@code Connection} представляет объект подключённому к серверу, который манипулирует {@link CollectionManager}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 15.05.19
 */
public class Connection extends Thread {

    private Socket incoming;
    private int masterID;
    private HashMap<String, AbstractCommand> availableCommands = new HashMap<>();

    /**
     * @param incoming активное соединение с клиентской программой.
     * Команды, доступные клиенту, являются объектами {@link AbstractCommand}, хранящимися в
     * {@code HashMap <String, AbstractCommand> availableCommands}.
     */
    Connection(Socket incoming) {
        this.incoming = incoming;
        availableCommands.put("login", new LoginCommand(this));
        availableCommands.put("register", new RegisterCommand());
        availableCommands.put("delete_account", new DeleteAccountCommand(this));
        availableCommands.put("man", new ManCommand(availableCommands));
        availableCommands.put("help", new HelpCommand(availableCommands));
    }


    /**
     * Поддерживает активное соединение с клиентом.
     */
    @Override
    public void run() {
        try (ObjectInputStream getFromClient = new ObjectInputStream(incoming.getInputStream());
             ObjectOutputStream sendToClient = new ObjectOutputStream(incoming.getOutputStream())) {
            sendToClient.writeObject("Соединение установлено.\nВы можете вводить команды.");
            AbstractCommand error = new AbstractCommand() {
                @Override
                public synchronized String execute() {
                    return "Неизвестная команда. Введите 'help' для получения списка команд.";
                }
            };
            while (true) {
                try {
                    String requestFromClient = (String) getFromClient.readObject();
                    System.out.print("Получено [" + requestFromClient + "] от " + incoming + ". ");
                    String[] parsedCommand = requestFromClient.trim().split(" ");
                    if (parsedCommand.length == 1)
                        sendToClient.writeObject(availableCommands.getOrDefault(parsedCommand[0], error).execute());
                    else sendToClient.writeObject(availableCommands.getOrDefault(parsedCommand[0], error)
                            .execute(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length)));
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

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public Socket getSocket() {
        return incoming;
    }

    public HashMap<String, AbstractCommand> getCommands() {
        return availableCommands;
    }

    public void setCommands(HashMap<String, AbstractCommand> availableCommands) {
        this.availableCommands = availableCommands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return masterID == that.masterID &&
                Objects.equals(incoming, that.incoming) &&
                Objects.equals(availableCommands, that.availableCommands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incoming, masterID, availableCommands);
    }

    @Override
    public String toString() {
        return "Connection{" +
                "incoming=" + incoming +
                ", masterID=" + masterID +
                ", availableCommands=" + availableCommands +
                '}';
    }
}

//TODO имена с пробелами