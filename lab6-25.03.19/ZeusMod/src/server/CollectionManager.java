package server;

import com.google.gson.*;
import tale.Shorty;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Класс {@code CollectionManager} обеспечивает доступ к коллекции.
 * @author Артемий Кульбако
 * @version 2.3
 * @since 24.04.19
 */
public final class CollectionManager {

    private List<Shorty> citizens;
    private Gson serializer;
    private Type collectionType;
    private File jsonCollection;
    private Date initDate;

    {
        citizens = Collections.synchronizedList(new LinkedList<>());
        serializer = new Gson();
        collectionType = new TypeToken<LinkedList<Shorty>>() {}.getType();
    }

    /**
     * Предоставляет доступ к коллекции и связанному с ней файлу.
     * @param collectionPath путь к файлу коллекции в файловой системе.
     */
    CollectionManager(String collectionPath) {
        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.err.println("Путь к файлу должен быть задан переменной окружения 'Collman_Path.'");
            System.exit(1);
        }
        jsonCollection = new File(collectionPath);
        try {
            String extension = jsonCollection.getAbsolutePath().substring(jsonCollection.getAbsolutePath().lastIndexOf(".") + 1);
            if (!jsonCollection.exists() | !extension.equals("json")) throw new FileNotFoundException();
            if (jsonCollection.length() == 0) throw new Exception("Файл пуст");
            if (!jsonCollection.canRead() || !jsonCollection.canWrite()) throw new SecurityException();
            try (BufferedReader collectionReader = new BufferedReader(new FileReader(jsonCollection))) {
                System.out.println("Загрузка коллекции " + jsonCollection.getAbsolutePath());
                String nextLine;
                StringBuilder result = new StringBuilder();
                while ((nextLine = collectionReader.readLine()) != null) result.append(nextLine);
                try {
                    citizens = serializer.fromJson(result.toString(), collectionType);
                    System.out.println("Коллекция успешно загружена. Добавлено " + citizens.size() + " элементов.");
                } catch (JsonSyntaxException ex) {
                    System.err.println("Ошибка синтаксиса JSON. " + ex.getMessage());
                    System.exit(1);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл по указанному пути не найден или он пуст.");
            System.exit(1);
        } catch (SecurityException e) {
            System.err.println("Файл защищён от чтения.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Что-то не так с файлом.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Файл пуст");
            System.exit(1);
        }
        initDate = new Date();
    }

    /**
     * Записывает элементы коллекции в файл. Так как необходим нескольким командам, реализован в этом классе.
     */
    public void save() {
        try (BufferedWriter writerToFile = new BufferedWriter(new FileWriter(jsonCollection))) {
            writerToFile.write(serializer.toJson(citizens));
        } catch (Exception ex) {
            System.out.println("Возникла непредвиденная ошибка. Коллекция не может быть сохранена.");
        }
    }

    public List<Shorty> getCitizens() {
        return citizens;
    }

    public File getJsonCollection() {
        return jsonCollection;
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
                Objects.equals(collectionType, that.collectionType) &&
                Objects.equals(jsonCollection, that.jsonCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citizens, serializer, collectionType, jsonCollection);
    }
}