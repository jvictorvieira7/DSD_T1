package Sockets;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

    public static void main(String[] args) throws IOException {

        System.out.println("Criando conexão...");

        try (Socket socket = new Socket("10.15.120.94", 80);) {
            System.out.println("Conectado com sucesso!");
            InputStream inputStream = socket.getInputStream();
            byte[] dadosBrutos = new byte[1024];
            int qtdBytesLidos = inputStream.read(dadosBrutos);
            while (qtdBytesLidos >= 0) {
                String dadosStream = new String(dadosBrutos, 0, qtdBytesLidos);
                System.out.println(dadosStream);
                qtdBytesLidos = inputStream.read(dadosBrutos);
            }

        } catch (UnknownHostException e) {
            System.out.println("Host não encontrado!");
            e.printStackTrace();
        }
    }




    //TODO Disponibiliza switch no terminal ao cliente: INSERT, UPDATE, GET, DELETE, LIST.



}
