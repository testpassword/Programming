package server.commands;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import server.managers.CollectionManager;
import tale.Shorty;
import java.util.LinkedList;

public class UpdateCommand extends AbstractCommand {

    public UpdateCommand() {
        setDescription("Обновить коллекцию.");
    }

    @Override
    public synchronized String execute(String[] arg) {
        try {
            CollectionManager.getInstance().getCitizens().clear();
            CollectionManager.getInstance().getCitizens().addAll(CollectionManager.getInstance().getSerializer().fromJson(arg[0], new TypeToken<LinkedList<Shorty>>(){}.getType()));
            return "Данные обновлены";
        } catch (JsonSyntaxException e) {
            return e.getLocalizedMessage();
        }
    }
}
