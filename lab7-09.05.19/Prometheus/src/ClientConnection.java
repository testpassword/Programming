import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Класс {@code ClientConnection} представляет объект клиента, подключаемый к серверу для манипулирования коллекцией.
 * @author Артемий Кульбако
 * @version 1.2
 * @since 21.05.19
 */
class ClientConnection {

    private Commander commands;
    private static ClientConnection instance;

    public static ClientConnection getInstance() {
        if (instance == null) {
            instance = new ClientConnection();
        }
        return instance;
    }

    /**
     * Устанавливает активное соединение с сервером.
     */
    void start() {
        Console console = System.console();
            while (true) {
                try (Socket outcoming = new Socket(InetAddress.getLocalHost(), 8800)) {
                    outcoming.setSoTimeout(5000);
                    try (ObjectOutputStream outputStream = new ObjectOutputStream(outcoming.getOutputStream());
                         ObjectInputStream inputStream = new ObjectInputStream(outcoming.getInputStream())) {
                        commands = new Commander(console, outputStream, inputStream);
                        commands.interactiveMode();
                        System.out.println("Завершение программы.");
                    }
                } catch (IOException e) {
                    System.err.println("Нет связи с сервером. Подключиться ещё раз ({да} или {нет})?");
                    String answer;
                    while (!(answer = console.readLine()).equals("да")) {
                        switch (answer) {
                            case "":
                                break;
                            case "нет":
                                System.exit(0);
                                break;
                                default: System.out.println("Введите корректный ответ.");
                        }
                    }
                    System.out.print("Подключение ...");
                    //TODO запоминает логин и пароль и автоматически подключается
                    continue;
                }
            }
        }
    }