package Exemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Máquina A
 *
 * <p>
 * Inicia um ServerSocket e aguarda conexão do cliente </p>
 * <p>
 * Ao receber a conexão, entra no modo de conversação no chat: </p>
 * <ul>
 * <li>Transmite a sentença digitada no prompt.</li>
 * <li>Aguarda o cliente enviar uma sentença de resposta e imprime.</li>
 * <li>Termina quando o usuário digitar sentença “exit”.</li>
 * </ul>
 *
 * @author Fernando Santos
 */
public class MaquinaA {

    public static void main(String[] args) throws IOException {
        /*
        * O Fernando está usando Linux. Neste sistema operacional, 
        * usuários que não não root/admin não conseguem fazer o bind 
        * em portas abaixo de 1024. 
        * Por este motivo, nesta implementação está utilizando 
        * a porta 65000 (uma porta aleatóriamente alta, que não deve
        * interferir em outros sistemas rodando na máquina).
         */
        ServerSocket server = new ServerSocket(65000);
        server.setReuseAddress(true);

        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n"); // rodar via NetBeans precisa disso para identificar o <enter>

        Socket conn = null;

        try {
            System.out.println("Servidor iniciado. Aguardando conexão...");
            conn = server.accept();
            System.out.println("Conexão recebida.");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            PrintWriter out = new PrintWriter(conn.getOutputStream(), true); // true para autoflush;

            System.out.println("Digite mensagem para enviar: ");
            String msgEnviar = scan.next();
            while (!msgEnviar.equals("exit")) {
                // enviar a sentença digitada pelo usuário
                out.println(msgEnviar);

                // ler sentença recebida
                System.out.println("Aguardando mensagem...");
                String msgRecebida = in.readLine();
                if (msgRecebida == null){
                    // se a outra máquina fechar a conexão (digitar exit) 
                    // então será recebido 'null' e podemos encerrar o chat.
                    System.out.println("Chat encerrado pelo outro usuário.");
                    break;
                }
                System.out.println("Mensagem recebida " + msgRecebida);
                System.out.println("Digite mensagem para enviar ('exit' para sair): ");
                msgEnviar = scan.next();
            }
        } catch (Exception e) {
            System.out.println("Deu exception");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
                System.out.println("Socket encerrado.");
            }
            server.close();
            scan.close();
            System.out.println("ServerSocket encerrado.");
        }
    }
}
