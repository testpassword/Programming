package commands;

import server.CollectionManager;
import java.util.Objects;

/**
 * Класс {@code AbstractCommand} является суперклассом для всех классов команд.
 * @author Артемий Кульбако
 * @version 1.7
 * @since 005.05.19
 */
public abstract class AbstractCommand {

    private CollectionManager manager; //Позволяет изменить коллекцию.
    private String description; //Содержит краткое руководство к команде.

    public AbstractCommand(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Метод служит для выполнения кода команды без агрументов.
     * @return строка, которая содержит результат операции.
     */
    public synchronized String execute() {
        return "Отсутствует аргумент.";
    }

    /**
     * Метод служит для выполнения кода команды с агрументами.
     * @param arg аргумент команды.
     * @return строка, которая содержит результат операции.
     */
    public synchronized String execute(String arg) {
        return execute();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public CollectionManager getManager() {
        return manager;
    }

    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCommand)) return false;
        AbstractCommand that = (AbstractCommand) o;
        return Objects.equals(manager, that.manager) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manager, description);
    }

    @Override
    public String toString() {
        return "AbstractCommand{" +
                "manager=" + manager +
                ", description='" + description + '\'' +
                '}';
    }
}