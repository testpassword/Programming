package server.commands;

import server.managers.PasswordManager;
import server.managers.DatabaseManager;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс {@code RegisterCommand} переопределяет метод {@code execute()} для осуществления регистрации в БД.
 * @author Артемий Кульбако
 * @version 1.5
 * @since 01.06.19
 */
public class RegisterCommand extends AbstractCommand {

    public RegisterCommand() {
        setDescription("Зарегистрироваться в системе. Синтаксис {register email}." +
                "\nПароль будет сгенерирован и отправлен на указанною почту.");
    }

    @Override
    public synchronized String execute(String[] args) {
        try {
            InternetAddress email = new InternetAddress(args[0]);
            email.validate();
            String password = PasswordManager.generateNewPassword();
            try (Connection connection = DatabaseManager.getInstance().getConnection()) {
                connection.setAutoCommit(false);
                PreparedStatement request = connection.prepareStatement("INSERT INTO users (email, password) " +
                        "SELECT ?, ? WHERE NOT EXISTS (SELECT email FROM users WHERE email = ?)");
                request.setString(1, email.toString());
                try {
                    request.setString(2, PasswordManager.getHash(password, "SHA1"));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    System.err.println("Пароль будет записан в БД без хэширования.");
                    request.setString(2, password);
                }
                request.setString(3, email.toString());
                if (request.executeUpdate() == 1) {
                    try {
                        sendPassword(email, password);
                        connection.commit();
                        return "Регистрация завершена. Пароль отправлен на указанную почту.";
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        connection.rollback();
                        return "Не удалось завершить регистрацию из-за ошибки на сервере. Попробуйте позже.";
                    }
                } else return "Указанная почта занята.";
            } catch (SQLException e) {
                e.printStackTrace();
                return "Произошла ошибка на стороне сервера при отправке запроса к БД. Попробуйте ещё раз позже.";
            }
        } catch (AddressException e) {
            return e.getMessage();
        }
    }

    private void sendPassword(InternetAddress email, String password) throws MessagingException {
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
            message.setSubject("Пароль для регистрации в lab7.");
            message.setText("Пароль: " + password);
            Transport.send(message);
            System.out.println("Пользователь с почтой " + email + " зарегистрировался в системе.");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}