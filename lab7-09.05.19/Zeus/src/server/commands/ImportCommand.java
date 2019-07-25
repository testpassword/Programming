package server.commands;

import com.google.gson.JsonSyntaxException;
import server.Connection;
import server.managers.CollectionManager;
import tale.Shorty;
import java.util.LinkedList;

/**
 * Класс {@code ImportCommand} переопределяет метод {@code execute()} для добавления
 * {@code Collection <? extends Shorty> col} который был получен от клиента к серверу коллекции.
 * @author Артемий Кульбако
 * @version 1.5
 * @since 21.05.19
 */
public class ImportCommand extends AbstractCommand {

    private Connection currentConnection;

    public ImportCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Добавить объекты из файла на вашем компьютере в коллекцию и сохранить их на сервере.");
    }

    @Override
    public synchronized String execute(String[] args) {
        CollectionManager manager = CollectionManager.getInstance();
            int beginSize = manager.getCitizens().size();
            try {
                LinkedList<Shorty> importedShorty = manager.getSerializer().fromJson(args[0], manager.getCollectionType());
                try {
                    importedShorty.forEach(p -> p.setMasterID(currentConnection.getMasterID()));
                } catch (NullPointerException e) {
                    return "null значения запрещены.";
                }
                manager.getCitizens().addAll(importedShorty);
                manager.save();
                return "Из вашего файла было добавлено " + (manager.getCitizens().size() - beginSize) + " элементов.";
            } catch (JsonSyntaxException ex) {
                return "Синтаксическая ошибка JSON в импортированных данных." + ex.getMessage();
            }
    }
}