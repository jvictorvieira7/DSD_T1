package Exemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Máquina B
 *
 * <p>
 * Cria um Socket para conectar com a Máquina A </p>
 * <p>
 * Ao estabelecer conexão, entra no modo de conversação no chat: </p>
 * <ul>
 * <li>Recebe a sentença enviada pelo servidor.</li>
 * <li>Transmite a sentença digitada no prompt.</li>
 * <li>Termina quando o usuário digitar sentença “exit”.</li>
 * </ul>
 *
 * @author Fernando Santos
 */
public class MaquinaB {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n"); // rodar via NetBeans precisa disso para identificar o <enter>

        Socket conn = null;

        try {
            conn = new Socket("127.0.0.1", 65000);
            System.out.println("Conexão estabelecida.");
            PrintWriter out = new PrintWriter(conn.getOutputStream(), true); // true para autoflush
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while (true) {
                // ler sentença recebida
                System.out.println("Aguardando mensagem...");
                String msgRecebida = in.readLine();
                if (msgRecebida == null){
                    // se a outra máquina fechar a conexão (digitar exit) 
                    // então será recebido 'null' e podemos encerrar o chat.
                    System.out.println("Chat encerrado pelo outro usuário.");
                    break;
                }
                
                System.out.println("Mensagem recebida: " + msgRecebida);

                // ler sentença do usuário e enviar
                System.out.println("Digite mensagem para enviar ('exit' para sair): ");
                String msgEnviar = scan.nextLine();
                if (msgEnviar.equals("exit")) {
                    break;
                }
                out.println(msgEnviar);
            }
        } catch (Exception e) {
            System.out.println("Deu exception");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
                System.out.println("Socket encerrado.");
            }
        }
    }
}
