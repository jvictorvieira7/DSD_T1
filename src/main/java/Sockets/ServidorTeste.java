package Sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Controller.PessoaController;
import Model.Aluno;
import Model.AulaParticular;
import Model.Pessoa;
import Model.Professor;
import Service.Mensagens;
import Service.PessoaService;

public class ServidorTeste {
    private static final int PORTA = 80;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println(Mensagens.SERVER_STARTED + PORTA);


            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Conexão recebida. " + socket.getInetAddress());
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String comando;
            while ((comando = in.readLine()) != null) {
                System.out.println("Comando recebido: " + comando);
                if (comando.equalsIgnoreCase("SAIR") || comando.equalsIgnoreCase("INVALIDO") || comando.equalsIgnoreCase("TIMEOUT")){
                    break;
                } else {
                    String resposta = processarComando(comando);
                    out.println(resposta);
                }
            }
        } catch (SocketException e){
            System.err.println("Conexão perdida com " + socket.getInetAddress());
        } catch (EOFException e) {
            System.err.println("Cliente desconectou abruptamente (EOFException).");
        } catch (IOException e) {
            System.err.println("Erro na comunicação: " + e.getMessage());
        } finally {
            try { socket.close();
                System.out.println("Conexão com " + socket.getInetAddress() + " finalizada.");
            } catch (IOException e) {}
        }
    }

    private static String processarComando(String comando) {
        String[] partes = comando.split(";");
        String operacao = partes[0].toUpperCase();
        String tipo = partes.length > 1 ? partes[1].toUpperCase() : "";

        try {
            switch (operacao) {
                case "INSERT":
                    return handleInsert(tipo, partes);
                case "UPDATE":
                    return handleUpdate(tipo, partes);
                case "GET":
                    return handleGet(tipo, partes);
                case "DELETE":
                    return handleDelete(tipo, partes);
                case "LIST":
                    return handleList(tipo);
                default:
                    return Mensagens.OPCAO_INVALIDA;
            }
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }


    private static String handleInsert(String opt, String[] partes) {

        switch (opt) {
            case "PESSOA":
                PessoaController pessoaController = new PessoaController(new PessoaService());
                Pessoa p = new Pessoa(partes[2], partes[3], partes[4]);
                return pessoaController.insertPessoa(p);
//            case "PROFESSOR":
//                Professor prof = new Professor(partes[2], partes[3], partes[4], partes[5]);
//                return Professor.Insert(prof);
//            case "ALUNO":
//                Aluno aluno = new Aluno(partes[2], partes[3], partes[4], partes[5]);
//                return Aluno.Insert(aluno);
//            case "AULA":
//                Professor pAula = Professor.Get(partes[4]);
//                Aluno aAula = Aluno.Get(partes[5]);
//                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
//                AulaParticular aula = new AulaParticular(data, Double.parseDouble(partes[3]), partes[6], pAula, aAula);
//                return AulaParticular.Insert(aula);
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }

    private static String handleUpdate(String opt, String[] partes) {
        switch (opt) {
            case "PESSOA":
                PessoaController pessoaController1 = new PessoaController(new PessoaService());
                Pessoa pNome = new Pessoa(partes[2], partes[3], partes[4]);
                return pessoaController1.updatePessoa(pNome, 1); // Atualiza nome
            case "ENDERECO":
                PessoaController pessoaController2 = new PessoaController(new PessoaService());
                Pessoa pEnd = new Pessoa(partes[2], "", partes[3]);
                return pessoaController2.updatePessoa(pEnd, 2); // Atualiza endereço
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }

    private static String handleGet(String opt, String[] partes) {
        switch (opt) {
            case "PESSOA":
                PessoaController pessoaController = new PessoaController(new PessoaService());
                return pessoaController.get(partes[2]);
//            case "PROFESSOR":
//                Professor prof = Professor.Get(partes[2]);
//                return prof != null ? "OK;" + prof.getCpf() + ";" + prof.getNome() + ";" + prof.getEndereco() + ";" + prof.getEspecializacao() : "ERRO;Professor não encontrado";
//            case "ALUNO":
//                Aluno aluno = Aluno.Get(partes[2]);
//                return aluno != null ? "OK;" + aluno.getCpf() + ";" + aluno.getNome() + ";" + aluno.getEndereco() + ";" + aluno.getMatricula() : "ERRO;Aluno não encontrado";
//            case "AULA":
//                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
//                AulaParticular aula = AulaParticular.Get(data, partes[3]);
//                return aula != null ? formatAula(aula) : "ERRO;Aula não encontrada";
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }

    private static String handleDelete(String opt, String[] partes) {
        switch (opt) {
            case "PESSOA":
                PessoaController pessoaController = new PessoaController(new PessoaService());
                return pessoaController.delete(partes[2]);
//            case "PROFESSOR":
//                return Professor.Delete(partes[2]);
//            case "ALUNO":
//                return Aluno.Delete(partes[2]);
//            case "AULA":
//                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
//                return AulaParticular.Delete(data, partes[3]);
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }


    private static String handleList(String opt) {
        switch (opt) {
            case "PESSOA":
                PessoaController pessoaController = new PessoaController(new PessoaService());
                return pessoaController.list();
//            case "PROFESSOR":
//                return formatList(Professor.List());
//            case "ALUNO":
//                return formatList(Aluno.List());
//            case "AULA":
//                return formatAulaList(AulaParticular.List());
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }


    private static String formatAula(AulaParticular aula) {
        return String.format("OK;%s;%.2f;%s;%s;%s;%s",
                aula.getDataHora().format(formatter),
                aula.getValor(),
                aula.getLocal(),
                aula.getProfessor().getCpf(),
                aula.getAluno().getCpf(),
                aula.getProfessor().getNome() + " - " + aula.getAluno().getNome());
    }


    private static String formatAulaList(List<AulaParticular> aulas) {
        StringBuilder sb = new StringBuilder("OK;");
        for (AulaParticular a : aulas) {
            sb.append(a.getDataHora().format(formatter)).append(";")
              .append(a.getProfessor().getCpf()).append(";")
              .append(a.getAluno().getCpf()).append("|");
        }
        return sb.length() > 3 ? sb.substring(0, sb.length()-1) : "OK;Nenhuma aula";
    }
}