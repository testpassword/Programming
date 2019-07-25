package server.commands;

import server.managers.CollectionManager;
import server.managers.DatabaseManager;
import server.managers.PasswordManager;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;

/**
 * Класс {@code DeleteAccountCommand} переопределяет метод {@code execute()} для удаление запись о пользователе из БД.
 * @author Артемий Кульбако
 * @version 1.2
 * @since 01.06.19
 */
public class DeleteAccountCommand extends AbstractCommand {

    private server.Connection currentConnection;

    public DeleteAccountCommand(server.Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Удалить свой аккаунт. Синтаксис {remove_account email password}. " +
                "Будут также удалены все ваши элементы.");
    }

    @Override
    public synchronized String execute(String[] args) {
            try {
                if (args.length < 2) throw new IllegalArgumentException();
                InternetAddress email = new InternetAddress(args[0]);
                email.validate();
                try (java.sql.Connection connection = DatabaseManager.getInstance().getConnection()) {
                    connection.setAutoCommit(false);
                    PreparedStatement request = DatabaseManager.getInstance().getConnection().
                            prepareStatement("DELETE FROM users WHERE email = ? AND password = ?");
                    request.setString(1, args[0]);
                    try {
                        request.setString(2, PasswordManager.getHash(args[1], "SHA1"));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        System.err.println("Пароль будет записан в БД без хэширования.");
                        request.setString(2, args[1]);
                    }
                    if (request.executeUpdate() == 1) {
                        try {
                        sendNotification(email);
                        CollectionManager.getInstance().getCitizens().removeIf(p -> p.getMasterID()
                                == currentConnection.getMasterID());
                        connection.commit();
                        return "Ваш аккаунт был удалён.";
                        } catch (MessagingException e) {
                            e.printStackTrace();
                            connection.rollback();
                            return "Не удалось удалить аккаунт из-за ошибки на сервере. Попробуйте позже.";
                        }
                    } else return "Пользователь с такими данными не найден.";
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

        private void sendNotification(InternetAddress email) throws MessagingException {
            try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("mail.properties")) {
                Properties prop = System.getProperties();
                prop.load(inputStream);
                Session session = Session.getDefaultInstance(prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(prop.getProperty("sender_email"), prop.getProperty("sender_password"));
                    }
                });
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(prop.getProperty("sender_email")));
                message.setRecipient(Message.RecipientType.TO, email);
                message.setSubject("Удаление аккаунта.");
                message.setText("Ваш аккаунт был удалён с " + currentConnection.getSocket().getInetAddress());
                Transport.send(message);
                System.out.println("Пользователь с почтой " + email + " удалил аккаунт.");
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
}