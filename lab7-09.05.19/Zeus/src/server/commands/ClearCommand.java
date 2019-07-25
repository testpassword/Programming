package server.commands;

import server.Connection;
import server.managers.CollectionManager;

/**
 * Класс {@code ClearCommand} переопределяет метод {@code execute()}, чтобы удалить все элементы из
 * {@code Collection <? extends Shorty> col} которые принадлежат пользователю.
 * @author Артемий Кульбако
 * @version 1.5
 * @since 21.05.19
 */
public class ClearCommand extends AbstractCommand {

    private Connection currentConnection;

    public ClearCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Очистить коллекцию.");
    }

    @Override
    public synchronized String execute() {
        CollectionManager manager = CollectionManager.getInstance();
        manager.getCitizens().removeIf(p -> (p != null && p.getMasterID() == currentConnection.getMasterID()));
        manager.save();
        return "Из коллекции удалены все ваши элементы.";
    }
}