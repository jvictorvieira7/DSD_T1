package Sockets;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Controller.AulaParticularController;
import Controller.PessoaController;
import Model.AulaParticular;
import Model.Pessoa;
import Service.AulaParticularService;
import Service.Mensagens;
import Service.PessoaService;

public class Servidor {
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

    private static PessoaService pessoaService = new PessoaService();
    private static PessoaController pessoaController = new PessoaController(pessoaService);
    private static AulaParticularService aulaService = new AulaParticularService(pessoaService);
    private static AulaParticularController aulaController = new AulaParticularController(aulaService, pessoaController);


    private static String handleInsert(String opt, String[] partes) {

        switch (opt) {
            case "PESSOA":

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
            case "AULA":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime data = LocalDateTime.parse(partes[2], formatter);

                    double valor = Double.parseDouble(partes[3]);
                    Pessoa professor = pessoaController.buscaPorCPF(partes[4]);
                    Pessoa aluno = pessoaController.buscaPorCPF(partes[5]);
                    AulaParticular aula = new AulaParticular(data, valor, partes[6], professor, aluno);
                    return aulaController.agendarAula(aula);
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }

    private static String handleUpdate(String opt, String[] partes) {
        switch (opt) {
            case "PESSOA":
//                PessoaController pessoaController1 = new PessoaController(new PessoaService());
                Pessoa pNome = new Pessoa(partes[2], partes[3], partes[4]);
                return pessoaController.updatePessoa(pNome, 1); // Atualiza nome
            case "ENDERECO":
//                PessoaController pessoaController2 = new PessoaController(new PessoaService());
                Pessoa pEnd = new Pessoa(partes[2], "", partes[3]);
                return pessoaController.updatePessoa(pEnd, 2); // Atualiza endereço
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }

    private static String handleGet(String opt, String[] partes) {
        switch (opt) {
            case "PESSOA":
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
            case "AULA":
                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
                return aulaController.buscarAula(data, partes[3]);
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }

    private static String handleDelete(String opt, String[] partes) {
        switch (opt) {
            case "PESSOA":
                aulaController.removerPorPessoa(partes[2]);
                return pessoaController.delete(partes[2]);
//            case "PROFESSOR":
//                return Professor.Delete(partes[2]);
//            case "ALUNO":
//                return Aluno.Delete(partes[2]);
//            case "AULA":
//                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
//                return AulaParticular.Delete(data, partes[3]);
            case "AULA":
                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
                return aulaController.cancelarAula(data, partes[3]);
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }


    private static String handleList(String opt) {
        switch (opt) {
            case "PESSOA":
                return pessoaController.list();
//            case "PROFESSOR":
//                return formatList(Professor.List());
//            case "ALUNO":
//                return formatList(Aluno.List());
//            case "AULA":
//                return formatAulaList(AulaParticular.List());
            case "AULA":
                return aulaController.listarAulas();
            default:
                return Mensagens.OPCAO_INVALIDA;
        }
    }
}