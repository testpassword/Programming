package commands;

import server.CollectionManager;

/**
 * Класс {@code SaveCommand} переопределяет метод {@code execute ()} для сохранения
 * {@code Collection <? extends Shorty> col} в файл.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class SaveCommand extends AbstractCommand {

    public SaveCommand(CollectionManager manager) {
        super(manager);
        setDescription("Сохраняет коллекцию.");
    }

    @Override
    public synchronized String execute() {
        try {
            getManager().save();
            return "Изменения сохранены.";
        } catch (Exception ex) {
            return "Произошло непредвиденная ошибка на сервере. Коллекция не может быть сохранена. Попробуйте ещё раз позже.";
        }
    }
}