package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Model.Aluno;
import Model.AulaParticular;
import Model.Pessoa;
import Model.Professor;

public class ServidorTeste {
    private static final int PORTA = 80;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);

            while (true) {
                Socket socket = serverSocket.accept();
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
                String resposta = processarComando(comando);
                out.println(resposta);
            }
        } catch (IOException e) {
            System.err.println("Erro na comunicação: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException e) {}
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
                case "GET":
                    return handleGet(tipo, partes);
                case "UPDATE":
                    return handleUpdate(tipo, partes);
                case "DELETE":
                    return handleDelete(tipo, partes);
                case "LIST":
                    return handleList(tipo);
                default:
                    return "ERRO;Operação inválida";
            }
        } catch (Exception e) {
            return "ERRO;" + e.getMessage();
        }
    }

    private static String handleInsert(String tipo, String[] partes) {
        switch (tipo) {
            case "PESSOA":
                Pessoa p = new Pessoa(partes[2], partes[3], partes[4]);
                return Pessoa.Insert(p);
            case "PROFESSOR":
                Professor prof = new Professor(partes[2], partes[3], partes[4], partes[5]);
                return Professor.Insert(prof);
            case "ALUNO":
                Aluno aluno = new Aluno(partes[2], partes[3], partes[4], partes[5]);
                return Aluno.Insert(aluno);
            case "AULA":
                Professor pAula = Professor.Get(partes[4]);
                Aluno aAula = Aluno.Get(partes[5]);
                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
                AulaParticular aula = new AulaParticular(data, Double.parseDouble(partes[3]), partes[6], pAula, aAula);
                return AulaParticular.Insert(aula);
            default:
                return "ERRO;Tipo inválido para INSERT";
        }
    }

    private static String handleGet(String tipo, String[] partes) {
        switch (tipo) {
            case "PESSOA":
                Pessoa p = Pessoa.Get(partes[2]);
                return p != null ? "OK;" + p.getCpf() + ";" + p.getNome() + ";" + p.getEndereco() : "ERRO;Pessoa não encontrada";
            case "PROFESSOR":
                Professor prof = Professor.Get(partes[2]);
                return prof != null ? "OK;" + prof.getCpf() + ";" + prof.getNome() + ";" + prof.getEndereco() + ";" + prof.getEspecializacao() : "ERRO;Professor não encontrado";
            case "ALUNO":
                Aluno aluno = Aluno.Get(partes[2]);
                return aluno != null ? "OK;" + aluno.getCpf() + ";" + aluno.getNome() + ";" + aluno.getEndereco() + ";" + aluno.getMatricula() : "ERRO;Aluno não encontrado";
            case "AULA":
                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
                AulaParticular aula = AulaParticular.Get(data, partes[3]);
                return aula != null ? formatAula(aula) : "ERRO;Aula não encontrada";
            default:
                return "ERRO;Tipo inválido para GET";
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

    private static String handleUpdate(String tipo, String[] partes) {
        switch (tipo) {
            case "PESSOA":
                Pessoa p = new Pessoa(partes[2], partes[3], partes[4]);
                return Pessoa.Update(p, 1); // Atualiza nome
            case "ENDERECO":
                Pessoa pEnd = new Pessoa(partes[2], "", partes[3]);
                return Pessoa.Update(pEnd, 2); // Atualiza endereço
            default:
                return "ERRO;Tipo inválido para UPDATE";
        }
    }

    private static String handleDelete(String tipo, String[] partes) {
        switch (tipo) {
            case "PESSOA":
                return Pessoa.Delete(partes[2]);
            case "PROFESSOR":
                return Professor.Delete(partes[2]);
            case "ALUNO":
                return Aluno.Delete(partes[2]);
            case "AULA":
                LocalDateTime data = LocalDateTime.parse(partes[2], formatter);
                return AulaParticular.Delete(data, partes[3]);
            default:
                return "ERRO;Tipo inválido para DELETE";
        }
    }

    private static String handleList(String tipo) {
        switch (tipo) {
            case "PESSOA":
                return formatList(Pessoa.List());
            case "PROFESSOR":
                return formatList(Professor.List());
            case "ALUNO":
                return formatList(Aluno.List());
            case "AULA":
                return formatAulaList(AulaParticular.List());
            default:
                return "ERRO;Tipo inválido para LIST";
        }
    }

    private static String formatList(List<? extends Pessoa> pessoas) {
        StringBuilder sb = new StringBuilder("OK;");
        for (Pessoa p : pessoas) {
            sb.append(p.getCpf()).append(":").append(p.getNome()).append("|");
        }
        return sb.length() > 3 ? sb.substring(0, sb.length()-1) : "OK;Nenhum registro";
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