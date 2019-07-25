package server.managers;

import com.google.gson.*;
import tale.Shorty;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

/**
 * Класс {@code CollectionManager} обеспечивает доступ к коллекции.
 * @author Артемий Кульбако
 * @version 2.7
 * @since 03.06.19
 */
public final class CollectionManager {

    private List<Shorty> citizens;
    private Gson serializer;
    private Type collectionType;
    private Date initDate;
    private boolean collInit;
    private static volatile CollectionManager instance;

    {
        citizens = Collections.synchronizedList(new LinkedList<>());
        serializer = new Gson();
        collectionType = new TypeToken<LinkedList<Shorty>>() {}.getType();
        collInit = false; //метка, сигнализирущая о статусе коллекции
    }

    /**
     * Предоставляет доступ к коллекции и связанному с ней файлу. Коллекция должна быть одна на приложение, поэтому
     * реализован singleton шаблон.
     */
    public static CollectionManager getInstance() {
        /*
         * Дублирующая переменная нужна для оптимизации. Поле instance объявлено как volatile, что заставляет программу
         * обновлять её значение из памяти каждый раз при доступе к переменной, тогда как значение обычной переменной
         * может быть записано в регистр процессора для более быстрого чтения. Использую дополнительную локальную
         * переменную, ускоряется работа приложения, так как значение поля обноаляется только тогда, когда действительно нужно.
         */
        CollectionManager instance2 = instance;
        if (instance2 == null) {
            synchronized (CollectionManager.class) {
                instance2 = instance;
                if (instance2 == null) instance = instance2 = new CollectionManager();
            }
        } return instance;
    }

    /**
     * Загружает коллекцию из базы данных.
     */
    private CollectionManager() {
        System.out.println("Инициализация коллекции " + DatabaseManager.getInstance().getDB_URL());
        System.out.println(load());
        collInit = true;
        initDate = new Date();
    }

    /**
     * Записывает элементы коллекции в базу данных. Так как необходим нескольким командам, реализован в этом классе.
     * @return результат операции
     */
    public String save() {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement request = conn.createStatement()) {
            conn.setAutoCommit(false);
            request.addBatch("DELETE FROM shortys");
            for (Shorty s: citizens) {
                String msg = "INSERT INTO shortys VALUES ('" + s.getName() + "', " + s.getX() +
                        ", " + s.getY() + ", '" + serializer.toJson(s.getBirthday()) + "', " +
                        s.getMasterID() + ")";
                request.addBatch(msg);
            }
            request.executeBatch();
            conn.commit();
            return "Изменения успешно сохранены.";
        } catch (SQLException e) {
            e.printStackTrace();

            return "Не удалось сохранить изменения из-за ошибки на сервере. Попробуйте ещё раз позже.";
        }
    }

    /**
     * Заполняет коллекцию данными из БД. Если коллекция загружается впервые, и возникает исключение, то метод выводит
     * стек трассировки и завершает работу программы, при возникновении исключений во время последующих загрузок только
     * выводится стек трассировки.
     * @return результат загружки коллекции.
     */
    public String load() {
        try (ResultSet answer = DatabaseManager.getInstance().getConnection().createStatement().
                executeQuery("SELECT * FROM shortys")) {
            citizens.clear();
            while (answer.next()) {
                String name = answer.getString("name");
                double x = answer.getDouble("position_x");
                double y = answer.getDouble("position_y");
                LocalDateTime birthday = serializer.fromJson(answer.getString("birthday"), LocalDateTime.class);
                int masterID = answer.getInt("masterID");
                citizens.add(new Shorty(name, x, y, birthday, masterID));
            }
            return "Коллекция загружена.";
        } catch (SQLException | JsonSyntaxException e) {
            e.printStackTrace();
            if (!collInit) System.exit(1);
            return "Возникла непредвиденная ошибка. Коллекция не может быть загружена сейчас. Попробуйте позже.";
        }
    }

    public List<Shorty> getCitizens() {
        return citizens;
    }

    public Gson getSerializer() {
        return serializer;
    }

    public Type getCollectionType() {
        return collectionType;
    }

    @Override
    public String toString() {
        return "Тип коллекции: " + citizens.getClass() +
                "\nТип элементов: " + Shorty.class +
                "\nДата инициализации: " + initDate +
                "\nКоличество элементов: " + citizens.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionManager)) return false;
        CollectionManager that = (CollectionManager) o;
        return Objects.equals(citizens, that.citizens) &&
                Objects.equals(serializer, that.serializer) &&
                Objects.equals(collectionType, that.collectionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citizens, serializer, collectionType);
    }
}