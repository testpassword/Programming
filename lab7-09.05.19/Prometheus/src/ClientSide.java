public class ClientSide {

    /**
     * Отправная точка клиента. Создает объект {@code ClientConnection}.
     * @param args массив по умолчанию в основном методе. Не используется здесь.
     */
    public static void main(String[] args) {
        System.out.println("Запуск клиентского модуля.\nПодключение к серверу ...");
        ClientConnection.getInstance().start();
        }
}