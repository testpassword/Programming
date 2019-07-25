package LabFive;

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
    private boolean wasStart;

    {
        wasStart = false;
        serializer = new Gson();
        citizens = new LinkedList<>();
        iterForCitizens = citizens.listIterator();
    }

    public CollectionManager(String collectionPath) throws IOException {
        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Путь до файла json нужно передать через переменную окружения Collman_Path.");
            System.exit(1);
        }
        File file = new File(collectionPath);
        try {
            if (file.exists()) this.jsonCollection = new File(collectionPath);
            else throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файл по указанному пути не существует.");
            System.exit(1);
        }
        this.load();
        this.initDate = new Date();
        wasStart = true;
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
            System.out.println("Нельзя удалить последний элемент коллекциию. Коллекция пуста.");
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
     * Удаляет из коллекции все элементы, превыщающие элемент-параметр.
     * @param person : Строка, которую необходимо десериализовать в объект класса Shorty, с которым будут сравниваться элементы коллекции.
     */
    public void remove_greater(String person) {
        if (citizens.size() != 0) {
            int beginSize = citizens.size();
            try {
                citizens.removeIf(p -> (p != null && p.compareTo(serializer.fromJson(person, Shorty.class)) > 0));
                System.out.println("Из коллекции удалено " + (beginSize - citizens.size()) + " элементов.");
                save();
            } catch (JsonSyntaxException ex) {
                System.out.println("Ошибка синтаксиса Json. Не удалось удалить элементы.");
            }
        }
        else System.out.println("Элемент не с чем сравнивать. Коллекция пуста.");
    }

    /**
     * Выводит все элементы коллекции.
     */
    public void show() {
        if (citizens.size() != 0) citizens.forEach(p -> System.out.println(serializer.toJson(p)));
        else System.out.println("Коллекция пуста.");
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
        if (citizens.size() != 0) {
            int beginSize = citizens.size();
            try {
                citizens.removeIf(p -> p.equals(serializer.fromJson(person, Shorty.class)));
                System.out.println("Из коллекции удалено " + (beginSize - citizens.size()) + " элементов.");
                save();
            } catch (JsonSyntaxException ex) {
                System.out.println("Ошибка синтаксиса Json. Не удалось удалить элемент.");
            }
        }
        else System.out.println("Элемент не с чем сравнивать. Коллекция пуста.");
    }

    /**
     *  Десериализует коллекцию из файла json.
     * @throws IOException если файл пуст или защищён.
     */
    public void load() throws IOException {
        int beginSize = citizens.size();
        try {
            if (!jsonCollection.canRead() || !jsonCollection.canWrite()) throw new SecurityException();
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения.");
            System.exit(1);
        }
        try {
            if (jsonCollection.length() == 0) throw new JsonSyntaxException("");
        } catch (JsonSyntaxException ex) {
            System.out.println("Файл пуст.");
            if (!wasStart) System.exit(1);
        }
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
                LinkedList<Shorty> addedShorty = serializer.fromJson(result.toString(), collectionType);
                for (Shorty s: addedShorty) {
                    if (!citizens.contains(s)) citizens.add(s);
                }
            } catch (JsonSyntaxException ex) {
                System.out.println("Ошибка синтаксиса Json. Коллекция не может быть загружена.");
                System.exit(1);
            }
            System.out.println("Коллекций успешно загружена. Добавлено " + (citizens.size() - beginSize) + " элементов.");
        }
    }

    /**
     * Удаляет из коллекции элемент по значения, согласно синтаксису.
     * @param person : Строка, которую необходимо десериализовать в объект класса Shorty, который будет удалён из коллекции.
     */
    public void remove(String person) {
        if (citizens.size() != 0) {
            try {
                Shorty mainCompetitor = serializer.fromJson(person, Shorty.class);
                if (citizens.remove(mainCompetitor)) {
                    System.out.println("Элемент успешно удалён.");
                    save();
                } else System.out.println("Такого элемента нет в коллекции.");
            } catch (JsonSyntaxException ex) {
                System.out.println("Ошибка синтаксиса Json. Не удалось удалить элемент.");
            }
        }
        else System.out.println("Элемент не с чем сравнивать. Коллекция пуста.");
    }

    /**
     * Добавляет в коллекцию элемент переданный в качестве параметра, если он меньше минимального элемента коллекции.
     * @param person : Строка, которую необходимо десериализовать в объект класса Shorty, который будет добавлен, если удовлетворяет условию.
     */
    public void add_if_min(String person) {
        if (citizens.size() != 0) {
            Shorty mainCompetitor = serializer.fromJson(person, Shorty.class);
            Shorty competitor = Collections.min(citizens);
                if (competitor.compareTo(mainCompetitor) > 0)
                    if (citizens.add(mainCompetitor)) {
                        System.out.println("Элемент успешно добавлен.");
                        save();
                    } else System.out.println("Не удалось добавить элемент.");
            }
        else System.out.println("Элемент не с чем сравнивать. Коллекция пуста.");
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
     */
    public void save() {
        try (BufferedWriter outputStreamWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((jsonCollection))))) {
            outputStreamWriter.write(serializer.toJson(citizens));
        } catch (Exception ex) {
            System.out.println("Возникла непредвиденная ошибка. Коллекция не может быть записана в файл. Мне жаль, что всё так вышло.");
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionManager)) return false;
        CollectionManager manager = (CollectionManager) o;
        return Objects.equals(citizens, manager.citizens) &&
                Objects.equals(jsonCollection, manager.jsonCollection) &&
                Objects.equals(initDate, manager.initDate) &&
                Objects.equals(iterForCitizens, manager.iterForCitizens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citizens, initDate, iterForCitizens);
    }
}