package server.managers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

/**
 * Класс {@code DatabaseManager} обеспечивает доступ к базе данных PostgreSQL.studs.
 * @author Артемий Кульбако
 * @version 1.3
 * @since 04.06.19
 */
public final class DatabaseManager {

    private String DB_URL;
    private String USER;
    private String PASSWORD;
    private static volatile DatabaseManager instance;

    {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("helios_db.properties")) {
            Properties prop = System.getProperties();
            prop.load(inputStream);
            DB_URL = prop.getProperty("url_address");
            USER = prop.getProperty("user");
            PASSWORD = prop.getProperty("password");
            System.out.println("База данных сконфигурирована с helios_db.properties.");
            Tunnel tunnel = new Tunnel("se.ifmo.ru", prop.getProperty("user"), prop.getProperty("password"),
                    2222, "pg", 8594, 5432);
            tunnel.connect();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Предоставляет доступ к БД. БД должна быть одна на приложение, поэтому реализован singleton шаблон.
     */
    public static DatabaseManager getInstance() {
        /*
         * Дублирующая переменная нужна для оптимизации. Поле instance объявлено как volatile, что заставляет программу
         * обновлять её значение из памяти каждый раз при доступе к переменной, тогда как значение обычной переменной
         * может быть записано в регистр процессора для более быстрого чтения. Использую дополнительную локальную
         * переменную, ускоряется работа приложения, так как значение поля обноаляется только тогда, когда действительно нужно.
         */
        DatabaseManager instance2 = instance;
        if (instance2 == null) {
            synchronized (DatabaseManager.class) {
                instance2 = instance;
                if (instance2 == null) instance = instance2 = new DatabaseManager();
            }
        }
        return instance;
    }

    /**
     * Инициализирует БД и осуществляет пробное подключение к ней.
     */
    private DatabaseManager() {
        try (Connection testConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             ResultSet testRequest = testConnection.createStatement().executeQuery("SELECT version()")) {
            System.out.println("Идёт установка связи с БД.");
            while (testRequest.next())
                System.out.println("Связь с БД установлена." + " Версия: " + testRequest.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Возвращает новое соединение с БД.
     * @return объект {@link Connection} для связи.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    /**
     * Нужен для удобства при отладке. Если OS == SunOS, значит программа запущена на гелиосе, и загружается
     * helios_db.properties, иначе локальная база данных. По факту, этот метод был бы не нужен, если бы к БД на гелиосе
     * можно было подключаться без SSH тунеля.
     * @return имя подходящено файла .properties.
     */
    @Deprecated
    private String chooseDBProperties() {
        if (System.getProperty("os.name").equals("SunOS")) return "helios_db.properties";
        else return "local_db.properties";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseManager)) return false;
        DatabaseManager manager = (DatabaseManager) o;
        return Objects.equals(DB_URL, manager.DB_URL) &&
                Objects.equals(USER, manager.USER) &&
                Objects.equals(PASSWORD, manager.PASSWORD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DB_URL, USER, PASSWORD);
    }

    @Override
    public String toString() {
        return "DatabaseManager{" +
                "DB_URL='" + DB_URL + '\'' +
                ", USER='" + USER + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                '}';
    }
}