package server.commands;

import server.managers.PasswordManager;
import server.managers.DatabaseManager;
import server.Connection;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;

/**
 * Класс {@code LoginCommand} переопределяет метод {@code execute()} для подключения к БД и поиска соответствия почты
 * и пароля в таблице users.
 * @author Артемий Кульбако
 * @version 1.2
 * @since 22.05.19
 */
public class LoginCommand extends AbstractCommand {

    private Connection currentConnection;

    public LoginCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Войти под своей учётной записью. Синтаксис {login email}.");
    }

    @Override
    public synchronized String execute(String[] args) {
            try {
                if (args.length < 2) throw new IllegalArgumentException();
                InternetAddress email = new InternetAddress(args[0]);
                email.validate();
                try {
                    PreparedStatement request = DatabaseManager.getInstance().getConnection().
                            prepareStatement("SELECT master_id FROM users WHERE email = ? AND password = ?");
                    request.setString(1, email.toString());
                    try {
                        request.setString(2, PasswordManager.getHash(args[1], "SHA1"));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        System.err.println("Обращение к БД без хэширования.");
                        request.setString(2, args[1]);
                    }
                    try (ResultSet answer = request.executeQuery()) {
                        int userID = 0;
                        while (answer.next()) userID = answer.getInt(1);
                        if (userID != 0) {
                            setAccess(userID);
                            return "Доступ разрешён.";
                        } else return "Пользователь с такими данными не найден.";
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "Произошла ошибка на стороне сервера. Попробуйте ещё раз позже.";
                }
            } catch (AddressException e) {
                return e.getMessage();
            } catch (IllegalArgumentException e) {
                return execute();
            }
    }

    private void setAccess(int ID) {
        currentConnection.setMasterID(ID);
        HashMap<String, AbstractCommand> com = currentConnection.getCommands();
        com.put("add", new AddCommand(currentConnection));
        com.put("add_if_min", new AddIfMinCommand(currentConnection));
        com.put("clear", new ClearCommand(currentConnection));
        com.put("import", new ImportCommand(currentConnection));
        com.put("info", new InfoCommand(currentConnection));
        com.put("load", new LoadCommand());
        com.put("remove_all", new RemoveAllCommand(currentConnection));
        com.put("remove", new RemoveCommand(currentConnection));
        com.put("remove_greater", new RemoveGreaterCommand(currentConnection));
        com.put("remove_last", new RemoveLastCommand(currentConnection));
        com.put("save", new SaveCommand());
        com.put("show", new ShowCommand(currentConnection));
        com.put("sort", new SortCommand());
        com.remove("login");
        com.remove("register");
        System.out.println("Пользователь " + currentConnection.getSocket() + " получил доступ к коллекции.");
    }
}