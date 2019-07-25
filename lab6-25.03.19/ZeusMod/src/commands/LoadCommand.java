package commands;

import com.google.gson.JsonSyntaxException;
import server.CollectionManager;
import tale.Shorty;
import java.io.*;
import java.util.LinkedList;

/**
 * Класс {@code LoadCommand} переопределяет метод {@code execute ()} для загрузки файла пути, указанному в
 * переменной окружения "Collman_Path".
 * @author Artemy Kulbako
 * @version 1.4
 * @since 04.05.19
 */
public class LoadCommand extends AbstractCommand {

    public LoadCommand(CollectionManager manager) {
        super(manager);
        setDescription("Перечитвает коллекцию с файла.");
    }

    @Override
    public synchronized String execute() {
        File collectionFile = getManager().getJsonCollection();
        String notificationToClient = "Возникли проблемы с файлом на сервере. Попробуйте ещё раз позже.";
        try {
            String extension = collectionFile.getAbsolutePath().substring(collectionFile.getAbsolutePath().lastIndexOf(".") + 1);
            if (!collectionFile.exists() | collectionFile.length() == 0 | !extension.equals("json"))
                throw new FileNotFoundException();
            if (!collectionFile.canRead()) throw new SecurityException();
            int beginSize = getManager().getCitizens().size();
            try (BufferedReader inputStreamReader = new BufferedReader(new FileReader(collectionFile))) {
                String nextLine;
                StringBuilder result = new StringBuilder();
                while ((nextLine = inputStreamReader.readLine()) != null) result.append(nextLine);
                try {
                    LinkedList<Shorty> addedShorty = getManager().getSerializer().fromJson(result.toString(), getManager().getCollectionType());
                    for (Shorty s: addedShorty) if (!getManager().getCitizens().contains(s)) getManager().getCitizens().add(s);
                } catch (JsonSyntaxException ex) {
                    return "Синтаксическая ошибка JSON. Коллекция не может быть загружена.\n";
                }
                getManager().save();
                return "Коллекцию успешно перечитано. Добавлено " + (getManager().getCitizens().size() - beginSize) + " новых элементов.\n";
        }
        } catch (SecurityException e) {
            System.out.println("Файл защищён от чтения.");
            return notificationToClient;
        } catch (FileNotFoundException e) {
            System.out.println("Файл по указанному пути не найден.");
            return notificationToClient;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return notificationToClient;
        }
    }
}