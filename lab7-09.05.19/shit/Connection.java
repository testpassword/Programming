package server.connections;

import server.CollectionManager;
import server.commands.AbstractCommand;
import java.util.HashMap;

/**
 * Класс {@code Connection} представляет методы для создания сущности пользователя на строне сервера.
 */
public abstract class Connection implements Runnable {

    private CollectionManager manager;
    private HashMap<String, AbstractCommand> availableCommands;

    public Connection(CollectionManager manager, HashMap<String, AbstractCommand> availableCommands) {
        this.manager = manager;
        this.availableCommands = availableCommands;
    }

    public CollectionManager getManager() {
        return manager;
    }

    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    public HashMap<String, AbstractCommand> getAvailableCommands() {
        return availableCommands;
    }

    public void setAvailableCommands(HashMap<String, AbstractCommand> availableCommands) {
        this.availableCommands = availableCommands;
    }
}