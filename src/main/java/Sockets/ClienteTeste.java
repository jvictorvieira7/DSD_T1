package Sockets;

import Service.Mensagens;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class ClienteTeste {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(Mensagens.SINTAXE);

        try (Socket socket = new Socket("127.0.0.1", 80);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            int count = 0;
            ExecutorService executor = Executors.newSingleThreadExecutor();

            while (true) {
                if (count < 3) {
                    System.out.print("\n> ");

                    Future<String> future = executor.submit(() -> scanner.nextLine());

                    String comando;
                    try {
                        comando = future.get(5, TimeUnit.SECONDS);
                    } catch (TimeoutException e) {
                        System.out.println(Mensagens.TIMEOUT);
                        out.println("TIMEOUT");
                        break;
                    }

                    if (comando.equalsIgnoreCase("SAIR")) {
                        System.out.print(Mensagens.CONEXAO_CLIENTE_CLOSE);
                        out.println(comando);
                        break;
                    }

                    out.println(comando);
                    String resposta = in.readLine();
                    System.out.println("Resposta: " + resposta);

                    if (resposta.equalsIgnoreCase(Mensagens.OPCAO_INVALIDA)) {
                        count++;
                    } else {
                        break;
                    }
                } else {
                    out.println("INVALIDO");
                    break;
                }
            }
            executor.shutdown();
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}