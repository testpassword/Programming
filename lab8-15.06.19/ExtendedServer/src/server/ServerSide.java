package server;

import server.managers.CollectionManager;
import server.managers.DatabaseManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    /**
    * Точка входа в программу. Управляет подключением к клиентам и созданием потоков для каждого из них.
     * @param args массив по умолчанию в основном методе. Не используется здесь.
     */
    public static void main(String[] args) {
        DatabaseManager.getInstance(); //Загрузить БД
        CollectionManager.getInstance(); //Загрузить коллекцию
        try (ServerSocket server = new ServerSocket(8800)) {
            System.out.print("Сервер начал слушать клиентов: " + "Порт " + server.getLocalPort() +
                    " / Адрес " + InetAddress.getLocalHost() + ".\nОжидаем подключения клиентов");
            Thread pointer = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print(" .");
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            pointer.setDaemon(true);
            pointer.start();
            while (true) {
                Socket incoming = server.accept();
                pointer.interrupt();
         //       System.out.println(incoming + " подключился к серверу.");
                new Connection(incoming).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}