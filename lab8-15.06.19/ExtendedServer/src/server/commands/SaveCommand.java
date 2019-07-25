package server.commands;

import server.managers.CollectionManager;

/**
 * Класс {@code SaveCommand} переопределяет метод {@code execute()} для сохранения
 * {@code Collection <? extends Shorty> col} в файл.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class SaveCommand extends AbstractCommand {

    public SaveCommand() {
        setDescription("Сохраняет коллекцию.");
    }

    @Override
    public synchronized String execute() {
        return CollectionManager.getInstance().save();
    }
}