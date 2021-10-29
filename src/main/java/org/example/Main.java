package org.example;

/**
 * Main class
 * Non-Blocking IO
 * Выбран не блокирующий вариант клиент-сервер, так как клиент бесконечно
 * набирает сообщения. Тоесть сервер работает только тогда, когда от клиента приходит какая-то информация
 * запрашивая инфу у канала, в который и отправляет данные клиент
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        Client client = new Client();
        new Thread(server::onServer, "Сервер").start();
        //Имитирую, что сначала включился сервер, а потом клиент
        Thread.sleep(2000);
        Thread clientThread = new Thread(client::onClient, "Клиент");
        clientThread.start();
        //Сделал заглушку для остановки программы, если клиент ввёл end
        while (true) {
            if (clientThread.isInterrupted()) {
                System.exit(0);
            }
        }
    }
}
