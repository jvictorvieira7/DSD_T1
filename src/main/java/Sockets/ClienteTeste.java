package Sockets;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTeste {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("""
            ===== SISTEMA DE GESTÃO =====
            Formatos de comando:
            
            PESSOAS:
              INSERT;PESSOA;CPF;NOME;ENDERECO
              GET;PESSOA;CPF
              UPDATE;PESSOA;CPF;NOVO_NOME;NOVO_ENDERECO
              UPDATE;ENDERECO;CPF;NOVO_ENDERECO
              DELETE;PESSOA;CPF
              LIST;PESSOA
            
            PROFESSORES:
              INSERT;PROFESSOR;CPF;NOME;ENDERECO;ESPECIALIZACAO
              GET;PROFESSOR;CPF
              DELETE;PROFESSOR;CPF
              LIST;PROFESSOR
            
            ALUNOS:
              INSERT;ALUNO;CPF;NOME;ENDERECO;MATRICULA
              GET;ALUNO;CPF
              DELETE;ALUNO;CPF
              LIST;ALUNO
            
            AULAS:
              INSERT;AULA;DATA(dd/MM/yyyy HH:mm);VALOR;CPF_PROFESSOR;CPF_ALUNO;LOCAL
              GET;AULA;DATA;CPF_PROFESSOR
              DELETE;AULA;DATA;CPF_PROFESSOR
              LIST;AULA
            
            Digite SAIR para encerrar
            ============================
            """);

        try (Socket socket = new Socket("127.0.0.1", 80);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            while (true) {
                System.out.print("\n> ");
                String comando = scanner.nextLine();
                
                if (comando.equalsIgnoreCase("SAIR")) break;
                
                out.println(comando);
                String resposta = in.readLine();
                
                if (resposta.startsWith("OK;")) {
                    System.out.println("Sucesso: " + resposta.substring(3));
                } else if (resposta.startsWith("ERRO;")) {
                    System.err.println("Erro: " + resposta.substring(5));
                } else {
                    System.out.println("Resposta: " + resposta);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}