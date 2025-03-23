package Sockets;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//Exemplo da aula. //TODO modificar.
public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);
        server.setReuseAddress(true);

        while (true) {
            System.out.println("Aguardando conexão...");
            try (Socket socket = server.accept()) {
                System.out.println("Conectando com: " + socket.getInetAddress().getHostAddress());
                OutputStream outputStream = socket.getOutputStream();
                String msg = "Olá mundo, do outro lado!";
                outputStream.write(msg.getBytes());
            }
        }
    }
}
