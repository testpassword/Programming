package server;

import server.commands.*;
import server.managers.CollectionManager;
import server.managers.DatabaseManager;
import server.managers.PasswordManager;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        //    sendToClient.writeObject("Соединение установлено.\nВы можете вводить команды.");
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

                    switch (parsedCommand[0]) {
                        case "login":
                            if (authenticate(parsedCommand[1], parsedCommand[2])) sendToClient.writeObject(masterID);
                            else sendToClient.writeObject(0);
                            break;
                        case "register":
                            String answerToClient = availableCommands.get("register").execute(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                            sendToClient.writeObject(answerToClient);
                            break;
                        case "delete_account":
                            answerToClient = availableCommands.get("delete_account").execute(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                            sendToClient.writeObject(answerToClient);
                            break;
                        default:
                            if (authenticate(parsedCommand[0], parsedCommand[1])) {
                                if (parsedCommand.length == 3)
                                    answerToClient = availableCommands.getOrDefault(parsedCommand[2], error).execute();
                                else answerToClient = availableCommands.getOrDefault(parsedCommand[2], error)
                                        .execute(Arrays.copyOfRange(parsedCommand, 3, parsedCommand.length));
                                sendToClient.writeObject(answerToClient);
                                System.out.println("Ответ " + answerToClient + " отправлен.");
                            } else sendToClient.writeObject("ACCESS_DENIED");
                            break;
                    }

                } catch (SocketException e) {
                    System.out.println(incoming + " отключился от сервера."); //Windows
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(incoming + " отключился от сервера."); //Unix
        }
    }

    private synchronized boolean authenticate(String login, String password) {
        int userID = 0;
        try {
            InternetAddress email = new InternetAddress(login);
            email.validate();
            PreparedStatement request = DatabaseManager.getInstance().getConnection().
                    prepareStatement("SELECT master_id FROM users WHERE email = ? AND password = ?");
            request.setString(1, email.toString());
            try {
                request.setString(2, PasswordManager.getHash(password, "SHA1"));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.err.println("Обращение к БД без хэширования.");
                request.setString(2, password);
            }
            try (ResultSet answer = request.executeQuery()) {
                while (answer.next()) userID = answer.getInt(1);
                if (userID != 0) {
                    setMasterID(userID);
                    availableCommands.put("add", new AddCommand(this));
                    availableCommands.put("add_if_min", new AddIfMinCommand(this));
                    availableCommands.put("clear", new ClearCommand(this));
                    availableCommands.put("import", new ImportCommand(this));
                    availableCommands.put("info", new InfoCommand(this));
                    availableCommands.put("load", new LoadCommand());
                    availableCommands.put("remove_all", new RemoveAllCommand(this));
                    availableCommands.put("remove", new RemoveCommand(this));
                    availableCommands.put("remove_greater", new RemoveGreaterCommand(this));
                    availableCommands.put("remove_last", new RemoveLastCommand(this));
                    availableCommands.put("save", new SaveCommand());
                    availableCommands.put("show", new ShowCommand());
                    availableCommands.put("sort", new SortCommand());
                    availableCommands.put("update", new UpdateCommand());
                    availableCommands.remove("login");
                    availableCommands.remove("register");
                    System.out.println("Пользователь " + this.getSocket() + " получил доступ к коллекции.");
                    return true;
                } else return false;
            }
        } catch (AddressException | SQLException e) {
            e.getMessage();
            return false;
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