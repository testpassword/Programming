import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Класс {@code Spammer} осуществляет замер времени для многопопточного и последовательного обращений к серверу.
 * РАБОТАЕТ НЕ ПРАВИЛЬНО!
 */
public class Spammer {

    private static int counter = 10;
    static long start_time;

    public static void main(String[] args) {
        System.out.println("Запускаю злюкера.");
        parallel();
        nonParallel();
    }

    /**
     * Параллельно обращение.
     * 1) Создаём отпечаток времени старта.
     * 2) Запускаем клиентов в потоках.
     * 3) После работы последнего замеряем время.
     */
    public static void parallel() {
        start_time = System.currentTimeMillis();
        Thread[] zombies = new Thread[counter];
        for (int i = 0; i < counter; i++) {
            zombies[i] = new Thread(() -> {
                try (Socket target = new Socket(InetAddress.getLocalHost(), 8800)) {
                        try (ObjectOutputStream outputStream = new ObjectOutputStream(target.getOutputStream());
                             ObjectInputStream inputStream = new ObjectInputStream(target.getInputStream())) {
                            inputStream.readObject();
                     //       while (true) { если убрать получится дудос
                                outputStream.writeObject("info");
                                inputStream.readObject();
                        //    }
                            counter--;
                            if (counter == 0) System.out.println("Параллельно: " + (System.currentTimeMillis() - start_time) + " millis");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Thread t: zombies) t.start();
    }

    /**
     * Последовательное обращение.
     * 1) Создаём отпечаток времени старта.
     * 2) Запускаем каждого клиента, только после работы предыдущего.
     * 3) После работы последнего замеряем время.
     */
    public static void nonParallel() {
        start_time = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            try (Socket target = new Socket(InetAddress.getLocalHost(), 8800)) {
                try (ObjectOutputStream outputStream = new ObjectOutputStream(target.getOutputStream());
                     ObjectInputStream inputStream = new ObjectInputStream(target.getInputStream())) {
                    inputStream.readObject();
                    outputStream.writeObject("info");
                    inputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Последовательно: " + (System.currentTimeMillis() - start_time) + " millis");
    }
}