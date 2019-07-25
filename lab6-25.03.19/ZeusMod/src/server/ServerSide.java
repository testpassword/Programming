package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {

    private static CollectionManager serverCollection = new CollectionManager(System.getenv("Collman_Path"));

    /**
    * Точка входа в программу. Управляет подключением к клиентам и созданием потоков для каждого из них.
     * @param args массив по умолчанию в основном методе. Не используется здесь.
     */
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8800)) {
            System.out.print("Сервер начал слушать клиентов. " + "\nПорт " + server.getLocalPort() +
                    " / Адрес " + InetAddress.getLocalHost() + ".\nОжидаем подключения клиентов ");
            Thread pointer = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print(".");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.print("\n");
                        Thread.currentThread().interrupt();
                    }
                }
            });
            pointer.setDaemon(true);
            pointer.start();
            while (true) {
                Socket incoming = server.accept();
                pointer.interrupt();
                System.out.println(incoming + " подключился к серверу.");
                Runnable r = new ServerConnection(serverCollection, incoming);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}