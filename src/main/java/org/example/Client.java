package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Класс клиента
 */
public class Client {

    /**
     * Метод подключается к серверу и передаёт на него сообщения, пока не введено end
     */
    public void onClient() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 23334);
            final SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(socketAddress);
            Scanner scanner = new Scanner(System.in);
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            String msg;
            while (true) {
                System.out.println("Enter message for server...");
                msg = scanner.nextLine();
                if ("end".equals(msg)) break;
                socketChannel.write(
                        ByteBuffer.wrap(
                                msg.getBytes(StandardCharsets.UTF_8)));
                Thread.sleep(2000);
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
            socketChannel.close();
            Thread.currentThread().interrupt();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
