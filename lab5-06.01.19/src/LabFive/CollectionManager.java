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
    private boolean wasStart;
    protected static HashMap<String, String> manual;

    {
        wasStart = false;
        serializer = new Gson();
        citizens = new LinkedList<>();
        manual = new HashMap<>();
        manual.put("remove_last", "Удалить последний элемент из коллекции");
        manual.put("add", "Добавить новый элемент в коллекцию");
        manual.put("remove_greater", "Удалить из коллекции все элементы, превышающие заданный");
        manual.put("show", "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        manual.put("clear", "Очистить коллекцию");
        manual.put("info", "Вывести в стандартный поток вывода информацию о коллекции.");
        manual.put("remove_all", "Удалить из коллекции все элементы, эквивалентные заданному");
        manual.put("load", "Перечитать коллекцию из файла");
        manual.put("remove", "Удалить элемент из коллекции по его значению");
        manual.put("add_if_min", "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        manual.put("exit", "Сохранить коллекцию в файл и завершить работу программы");
        manual.put("sort", "Отсортировать элементы коллекции по указанному параметру. Аргументы: -n по имени, -m по деньгам, -h по хэш-коду");
    }

    public CollectionManager(String collectionPath) throws IOException {
        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Путь до файла json нужно передать через переменную окружения Collman_Path.");
            System.exit(1);
        }
        this.jsonCollection = new File(collectionPath);
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
        if (citizens.size() != 0) citizens.forEach(p -> System.out.println(serializer.toJson(p) + " hashCode=" + p.hashCode()));
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
            if (!jsonCollection.exists()) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файла по указанному пути не существует.");
            if (!wasStart) System.exit(1);
            else return;  //без return выкидввает exception, если удалить файл во время работы программы и вызвать load
        }
        try {
            if (!jsonCollection.canRead() || !jsonCollection.canWrite()) throw new SecurityException();
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения.");
            if (!wasStart) System.exit(1);
            else return; //без return выкидввает exception, если удалить файл во время работы программы и вызвать load
        }
        try {
            if (jsonCollection.length() == 0) throw new JsonSyntaxException("");
        } catch (JsonSyntaxException ex) {
            System.out.println("Файл пуст.");
            if (!wasStart) System.exit(1);
            else return; //без return выкидввает exception, если удалить файл во время работы программы и вызвать load
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
                if (citizens.remove(serializer.fromJson(person, Shorty.class))) {
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
     * Сортирует коллекция согласно указанному ключу.
     * @param arg -n по имени, -m по деньгам, -h по хэш-коду.
     */
    public void sort(String arg) {
        switch (arg) {
            case "-n":
                citizens.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                save();
                System.out.println("Коллекция отсортирована по именам (по возрастания).");
                break;
            case "-h":
                citizens.sort((o1, o2) -> o1.hashCode() - o2.hashCode());
                save();
                System.out.println("Коллекция отсортирована по хэш-коду (по возрастания).");
                break;
            case "-m":
                citizens.sort((o1, o2) -> (int)
                        ((o1.getWallet().getMoney() + o1.getAccount().getMoney() + o1.getWallet().getStocks() * Town.GiganticPlantSociety.getStockPriceForSold())
                        - (o2.getWallet().getMoney() + o2.getAccount().getMoney() + o2.getWallet().getStocks() * Town.GiganticPlantSociety.getStockPriceForSold())));
                save();
                System.out.println("Коллекция отсортирована по суммарному капиталу (по возрастания).");
                break;
            default: System.out.println("Неверный аргумент. Синтаксис 'sort -{n/m/h}'.");
        }
    }

    /**
     * Выводит на экран список доступных пользователю команд.
     */
    public void help() {
        System.out.println("Lab4 made by Artemy Kulbako, build 3\n" +
                "Данные коллекции сохраняются автоматически после каждой успешной модификации.");
        System.out.println("Команды: " + manual.keySet() + "\nman {команда} для справки.");
    }

    /**
     * Выводит справку для конкретной команды.
     * @param arg : Имя команды.
     */
    public void man(String arg) {
        System.out.println(manual.get(arg));
    }

    /**
     * Сериализует коллекцию в файл json.
     */
    public void save() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((jsonCollection))))) {
            writer.write(serializer.toJson(citizens));
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
                Objects.equals(jsonCollection, manager.jsonCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citizens);
    }
}