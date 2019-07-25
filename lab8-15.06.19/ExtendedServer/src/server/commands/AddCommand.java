package server.commands;

import com.google.gson.JsonSyntaxException;
import server.Connection;
import server.managers.CollectionManager;
import tale.Shorty;

/**
 * Класс {@code AddCommand} переопределяет метод {@code execute()} для добавления {@link Shorty} в коллекцию.
 * @author Артемий Кульбако
 * @version 1.5
 * @since 21.05.19
 */
public class AddCommand extends AbstractCommand {

    private Connection currentConnection;

    public AddCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Добавить новый элемент в коллекцию.");
    }

    @Override
    public synchronized String execute(String[] args) {
            try {
                CollectionManager manager = CollectionManager.getInstance();
                Shorty shorty = manager.getSerializer().fromJson(args[0], Shorty.class);
                shorty.setMasterID(currentConnection.getMasterID());
                manager.getCitizens().add(shorty);
                manager.save();
                return "Элемент успешно добавлен.";
            } catch (JsonSyntaxException ex) {
                return "Синтаксическая ошибка JSON. " + ex.getMessage() + " Не удалось добавить элемент.";
            } catch (NullPointerException e) {
                return "null значения запрещены.";
            }
    }
}