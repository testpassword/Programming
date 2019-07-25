package server.commands;

import server.managers.CollectionManager;

/**
 * Класс {@code LoadCommand} переопределяет метод {@code execute()} для загрузки файла пути, указанному в
 * переменной окружения "Collman_Path".
 * @author Artemy Kulbako
 * @version 1.5
 * @since 21.05.19
 */
public class LoadCommand extends AbstractCommand {

    public LoadCommand() {
        setDescription("Перечитвает коллекцию с файла.");
    }

    @Override
    public synchronized String execute() {
        return CollectionManager.getInstance().load();
    }
}