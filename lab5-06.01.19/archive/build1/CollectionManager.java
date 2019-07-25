import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class CollectionManager {

    private LinkedList<Shorty> citizens;
    private File jsonCollection;
    private Date initDate;
    private Gson serializer;
    private ListIterator<Shorty> iterForCitizens;
    private Shorty competitor;

    {
        serializer = new Gson();
        citizens = new LinkedList<>();
        iterForCitizens = citizens.listIterator();
    }

    public CollectionManager(String collectionPath) throws IOException {
        if (collectionPath == null) throw new FileNotFoundException("Путь до файла json нужно передать через переменную окружения Collman_Path.");
        this.jsonCollection = new File(collectionPath);
        this.load();
        this.initDate = new Date();
    }

    /**
     * Удаляет последний элемент коллекции.
     */
    public void remove_last() {
        try {
            citizens.removeLast();
            System.out.println("Последний элемент коллекции удалён.");
            save();
        }
        catch (NoSuchElementException ex) {
            System.out.println("Последний элемент в коллекции отсутствует. Возможно она пуста.");
        }
    }

    /**
     * Добавляет в коллекцию элемент, согласно синтаксису.
     * @param person : Строка, которую необходимо десериализовать в объект класса Shorty, который будет добавлен в коллекцию.
     */
    public void add(String person) {
        try {
            if (citizens.add(serializer.fromJson(person, Shorty.class))) {
                System.out.println("Элемент успешно добавлен.");
                save();
            }
        } catch (JsonSyntaxException ex) {
            System.out.println("Ошибка синтаксиса Json. Не удалось добавить элемент.");
        }
    }

    /**
     * Удаляет из коллекции все элементы, превыщающие параметр.
     * @param person : Строка, которую необходимо десериализовать в объект класса Shorty, с которым будут сравниваться элементы коллекции.
     */
    public void remove_greater(String person) {
        try {
            Shorty mainCompetitor = serializer.fromJson(person, Shorty.class);
            while (iterForCitizens.hasNext()) {
                competitor = iterForCitizens.next();
                if (mainCompetitor.compareTo(competitor) < 0)
                    if (citizens.remove(competitor)) 
                        System.out.println("Элемент " + competitor + " удалён из коллекции.");
                    else System.out.println("Не удалось удалить элемент " + competitor + " из коллекции.");
            }
            save();
        } catch (JsonSyntaxException ex) {
            System.out.println("Ошибка синтаксиса Json. Не удалось удалить элементы.");
        }
    }

    /**
     * Выводит все элементы коллекции.
     */
    public void show() {
        for (Shorty s: citizens) System.out.println(serializer.toJson(s));
    }

    /**
     * Удаляет все элементы коллекции.
     */
    public void clear() {
        citizens.clear();
        System.out.print("Коллекция очищена.");
        save();
    }

    /**
     * Удаляет все элементы из коллекции равные параметру.
     * @param person : Строка, которую необходимо десериализовать в объект класса Shorty, с которым будут сравниваться элементы коллекции.
     */
    public void remove_all(String person) {
        try {
            Shorty mainCompetitor = serializer.fromJson(person, Shorty.class);
            while (iterForCitizens.hasNext()) {
                competitor = iterForCitizens.next();
                if (mainCompetitor.equals(competitor))
                    if (citizens.remove(competitor)) System.out.println("Элемент " + competitor + " удалён из коллекции.");
                    else System.out.println("Не удалось удалить элемент " + competitor + " из коллекции.");
            }
            save();
        } catch (JsonSyntaxException ex) {
            System.out.println("Ошибка синтаксиса Json. Не удалось удалить элементы.");
        }
    }

    /**
     *  Десериализует коллекцию из файла json.
     * @throws JsonSyntaxException
     * @throws IOException
     * @throws SecurityException
     */
    public void load() throws IOException, SecurityException {
        if (!jsonCollection.canRead() || !jsonCollection.canWrite()) throw new SecurityException("Файл защищён от чтения и/или записи. " +
                "Для работы программы нужны оба разрешения.");
        if (jsonCollection.length() == 0) throw new JsonSyntaxException("Файл пуст.");
        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonCollection)))) {
            System.out.println("Идёт загрузка коллекции " + jsonCollection.getAbsolutePath());
            String nextLine;
            StringBuilder result = new StringBuilder();
            while ((nextLine = inputStreamReader.readLine()) != null) {
                result.append(nextLine);
            }
            Type collectionType = new TypeToken<LinkedList<Shorty>>() {
            }.getType();
            try {
                citizens = serializer.fromJson(result.toString(), collectionType);
            } catch (JsonSyntaxException ex) {
                System.out.println("Ошибка синтаксиса Json. Коллекция не может быть загружена.");
                System.exit(1);
            }
            System.out.println("Коллекций успешно загружена.");
        }
    }

    /**
     * Удаляет из коллекции элемент по значения, согласно синтаксису.
     * @param person : Строка, которую необходимо десериализовать в объект класса Shorty, который будет удалён из коллекции.
     */
    public void remove(String person) {
        try {
            Shorty mainCompetitor = serializer.fromJson(person, Shorty.class);
            if (citizens.remove(mainCompetitor)) {
                System.out.println("Элемент успешно удалён.");
                save();
            }
            else System.out.println("Не удалось удалить элемент.");
        } catch (JsonSyntaxException ex) {
            System.out.println("Ошибка синтаксиса Json. Не удалось удалить элемент.");
        }
    }

    /**
     * Добавляет в коллекцию элемент переданный в качестве параметра, если он меньше минимального элемента коллекции.
     * @param person : Строка, которую необходимо десериализовать в объект класса Shorty, который будет добавлен, если удовлетворяет условию.
     */
    public void add_if_min(String person) {
        Shorty mainCompetitor = serializer.fromJson(person, Shorty.class);
        if (citizens.size() == 0) System.out.println("Элемент " + mainCompetitor + " не с чем сравнивать. Коллекция пуста.");
        else {
            competitor = Collections.min(citizens);
            if (competitor.compareTo(mainCompetitor) > 0)
                if (citizens.add(mainCompetitor)) {
                    System.out.println("Элемент успешно добавлен.");
                    save();
            }
                else System.out.println("Не удалось добавить элемент.");
        }
    }

    /**
     * Выводит на экран список доступных пользователю команд.
     */
    public void help() {
        System.out.println("remove_last: удалить последний элемент из коллекции");
        System.out.println("add {element}: добавить новый элемент в коллекцию");
        System.out.println("remove_greater {element}: удалить из коллекции все элементы, превышающие заданный");
        System.out.println("show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.println("clear: очистить коллекцию");
        System.out.println("info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        System.out.println("remove_all {element}: удалить из коллекции все элементы, эквивалентные заданному");
        System.out.println("load: перечитать коллекцию из файла");
        System.out.println("remove {element}: удалить элемент из коллекции по его значению");
        System.out.println("add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        System.out.println("exit: сохранить коллекцию в файл и завершить работу программы");
    }

    /**
     * Сериализует коллекцию в файл json. Пользователь не должен иметь прямого доступа к методу.
     * @throws JsonSyntaxException
     * @throws IOException
     * @throws SecurityException
     */
    public void save() {
        try (BufferedWriter outputStreamWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((jsonCollection))))) {
            outputStreamWriter.write(serializer.toJson(citizens));
        } catch (Exception ex) {
            System.out.println("Возникла непредвиденная ошибка. Коллекция не может быть записана в файл. Мне жаль, что всё так вышло.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionManager)) return false;
        CollectionManager manager = (CollectionManager) o;
        return Objects.equals(citizens, manager.citizens) &&
                Objects.equals(jsonCollection, manager.jsonCollection) &&
                Objects.equals(initDate, manager.initDate) &&
                Objects.equals(serializer, manager.serializer) &&
                Objects.equals(iterForCitizens, manager.iterForCitizens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citizens, jsonCollection, initDate, serializer, iterForCitizens);
    }

    /**
     * Выводит информацию о коллекции.
     */
    @Override
    public String toString() {
        return "Тип коллекции: " + citizens.getClass() +
                "\nДата инициализации: " + initDate +
                "\nКоличество элементов: " + citizens.size();
    }
}