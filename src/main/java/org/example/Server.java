package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Класс Сервера
 */
public class Server {


    /**
     * Метод читает сообщения до тех пор, пока клиент не перестанет их печатать
     */
    public void onServer() {
        try {
            final ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress("localhost", 23334));
            while (true) {
                SocketChannel socketChannel = serverChannel.accept();
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    final String msgClearGaps = deleteGaps(msg);
                    inputBuffer.clear();
                    System.out.println("Получено сообщение от клиента: " + msg);
                    socketChannel.write(ByteBuffer.wrap(("Ответ сервера: " + msgClearGaps).getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String deleteGaps(String str) {
        return str.replace(" ", "");
    }
}
